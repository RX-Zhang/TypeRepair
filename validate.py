"""
Bug 验证模块，进行补丁验证。
"""
import logging
import os
import re
import subprocess
from typing import List, Dict
import javalang

from subprocess import TimeoutExpired

import settings
from settings import Options
from utils import get_bug_code, get_unified_diff, save_diff, get_patch_list, save_patch_in_result, cd, save_reason, \
    generate_secure_string

logger = logging.getLogger("CodeRepair")

# 将 defects4j 加入 PATH
os.environ['PATH'] = settings.d4j_bin_path + os.environ.get('PATH', '')
os.environ["JAVA_TOOL_OPTIONS"] = "-Duser.language=en -Duser.region=US"


class Validate:
    def __init__(
        self,
        options: Options,
        bug_dict: Dict[str, Dict[str, str | int]] | None,
    ) -> None:
        self.options = options
        self.reason_list = ["else", "syntax_error", "semantics_error", "timeout", "No"]
        self.bug_dict = bug_dict

    def validate_patch(self, repair_result: List[str], fix_idx: int):
        logger.info("======= Start validating patch =======")
        bug_code = get_bug_code(self.options)

        for candidate_idx, patch in enumerate(repair_result):
            diff = get_unified_diff(bug_code, patch)
            save_diff(self.options, diff, fix_idx, candidate_idx)
            if self.options.dataset_type == "java":
                have_errors, reason = self.validate_java_single_bug(patch, self.bug_dict)
            else:
                have_errors, reason = self.validate_py_single_bug(patch)
            if have_errors:
                save_patch_in_result(self.options, patch, fix_idx, candidate_idx, is_success=False)
            else:
                save_patch_in_result(self.options, patch, fix_idx, candidate_idx, is_success=True)
            save_reason(self.options, reason, fix_idx, candidate_idx)

        logger.info("======= Verification patch completed =======")


    def validate_java_single_bug(self, patch: str, bug_dict: Dict[str, Dict[str, str | int]]) -> (bool, str):
        logger.info("start to validate patch")
        have_errors: bool = True
        reason = self.reason_list[0]  # todo

        bug = self.options.sample  # eg: Chart-1
        project = bug.split("-")[0]  # eg: Chart
        bug_id = bug.split("-")[1]  # eg: 1
        bug_start = bug_dict[bug]['start']
        bug_end = bug_dict[bug]['end']
        random_name = generate_secure_string(3)
        tmp_bug_path = f"/tmp/{random_name}/test_{bug}"

        # 清除旧目录
        if os.path.exists(tmp_bug_path):
            subprocess.run(["rm", "-rf", tmp_bug_path], check=True)

        subprocess.run(["defects4j", "checkout", "-p", project, "-v", f"{bug_id}b", "-w", tmp_bug_path], check=True)

        # 获取源码目录
        loc_folder = "data/Defects4j/location"
        try:
            with open(f"{loc_folder}/{bug}.buggy.lines", "r") as f:
                bug_locs = f.read()
        except FileNotFoundError:
            logger.info(f"Failed to read file: {loc_folder}/{bug}.buggy.lines")
            subprocess.run(["rm", "-rf", tmp_bug_path], check=True)
            raise FileNotFoundError(f"Failed to read file: {loc_folder}/{bug}.buggy.lines")
        locs = set([x.split("#")[0] for x in bug_locs.splitlines()])  # should only be one
        if len(locs) != 1:
            logger.info("The bug is not in a single function!,return")
            subprocess.run(["rm", "-rf", tmp_bug_path], check=True)
            raise TypeError("The bug is not in a single function!")
        loc_path = locs.pop()

        # get source code
        source_dir_result = subprocess.run(
            ["defects4j", "export", "-p", "dir.src.classes", "-w", tmp_bug_path],
            capture_output=True,
            text=True,
            check=True
        )
        source_dir = source_dir_result.stdout.strip().splitlines()[-1].strip()
        try:
            with open(f"{tmp_bug_path}/{source_dir}/{loc_path}", 'r') as f:
                source = f.readlines()
        except:
            with open(f"{tmp_bug_path}/{source_dir}/{loc_path}", 'r', encoding='ISO-8859-1') as f:
                source = f.readlines()

        # replace bug
        patch_list = get_patch_list(patch)
        repair_source = "".join(source[:bug_start - 1] + patch_list + source[bug_end:])
        try:
            with open(f"{tmp_bug_path}/{source_dir}/{loc_path}", 'w') as f:
                f.write(repair_source)
        except:
            with open(f"{tmp_bug_path}/{source_dir}/{loc_path}", 'w', encoding='ISO-8859-1') as f:
                f.write(repair_source)

        # syntax error check
        try:
            tokens = javalang.tokenizer.tokenize(repair_source)
            parser = javalang.parser.Parser(tokens)
            parser.parse()
            # 目标目录中使用 defects4j compile 编译该项目。
            subprocess.run(["defects4j", "compile", "-w", tmp_bug_path], check=True)
        except:
            reason = self.reason_list[1]
            logger.info("There is a syntax error in the current patch, return.")
            subprocess.run(["rm", "-rf", tmp_bug_path], check=True)
            return have_errors, reason

        # Semantic Check
        try:
            # 使用 defects4j test 命令运行该项目的所有单元测试。
            result = subprocess.run(["defects4j", "test", "-w", tmp_bug_path],
                            stdout=subprocess.PIPE,
                            stderr=subprocess.PIPE,
                            timeout=180,
                            check=False)
            res_stderr = result.stderr.decode('utf-8')
            res_stdout = result.stdout.decode('utf-8').strip().splitlines()

            # 检查是否有编译错误
            for line in res_stderr.splitlines():
                if re.search(r':\serror:\s', line):
                    reason = self.reason_list[2]
                    logger.info("There is a syntax error in the current patch, return.")
                    subprocess.run(["rm", "-rf", tmp_bug_path], check=True)
                    return have_errors, reason

            # 判断测试是否失败
            if any("Failing tests: 0" in line for line in res_stdout):
                have_errors = False
                reason = self.reason_list[4]
                logger.info("The current patch has no syntax or semantic errors.")
                subprocess.run(["rm", "-rf", tmp_bug_path], check=True)
                return have_errors, reason
            else:
                reason = self.reason_list[2]
                logger.info("The current patch contains semantic errors and has not passed all tests, return")
                subprocess.run(["rm", "-rf", tmp_bug_path], check=True)
                return have_errors, reason
        except TimeoutExpired:
            reason = self.reason_list[3]
            logger.info("Test function timeout, return")
            subprocess.run(["rm", "-rf", tmp_bug_path], check=True)
            return have_errors, reason
        except Exception as e:
            logger.info("Other errors occurred in the test error, return")
            subprocess.run(["rm", "-rf", tmp_bug_path], check=True)
            return have_errors, reason


    def validate_py_single_bug(self, patch) -> (bool, str):
        logger.info("start to validate patch")
        have_errors: bool = True
        reason = self.reason_list[0]  # todo

        # validate SyntaxError
        try:
            compile(patch, "<string>", "exec")
        except SyntaxError:
            reason = self.reason_list[1]
            logger.info("There is a syntax error in the current patch, return.")
            return have_errors, reason

        # validate Semantic

        # write file
        quixbugs_bug_path = f"{self.options.qbs_test_path}/python_programs"
        try:
            with open(f"{quixbugs_bug_path}/{self.options.sample}.py", "w") as f:
                f.write(patch)
        except FileNotFoundError:
            raise FileNotFoundError(f"{quixbugs_bug_path} does not exist!")

        try:
            with cd(self.options.qbs_test_path):
                result = subprocess.run(
                    ["pytest", "--timeout=30", f"python_testcases/test_{self.options.sample}.py"],
                    capture_output=True,  # 捕获输出
                    text=True)
            test_output = result.stdout

            pattern = re.compile(r'\d+ passed')
            for line in test_output.splitlines():
                if "ERRORS" in line:
                    reason = self.reason_list[1]
                    logger.info("There is a syntax error in the current patch, return.")
                    return have_errors, reason
                if pattern.search(line.strip()):
                    have_errors = False
                    reason = self.reason_list[4]
                    logger.info("The current patch has no syntax or semantic errors.")
                    return have_errors, reason

            reason = self.reason_list[2]
            logger.info("The current patch contains semantic errors and has not passed all tests, return")
            return have_errors, reason

        except:
            logger.info("Other errors occurred in the test error, return")
            return have_errors, reason