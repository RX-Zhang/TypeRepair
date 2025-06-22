import json
import logging
import os
import subprocess
import tiktoken
from contextlib import contextmanager
from difflib import unified_diff
from typing import List, Dict
from pathlib import Path
from settings import Options
import secrets
import string


logger = logging.getLogger("CodeRepair")

def setup_logger(log_dir: str, verbose: bool = False) -> None:
    """
    创建并配置一个日志记录器，同时输出到控制台和文件。

    参数:
        log_dir (str): 存放日志文件的目录，默认为 'logs'。
        verbose (bool): 是否启用详细日志（DEBUG级别），默认为 False。

    返回:
        logging.Logger: 配置好的日志记录器对象。
    """
    os.makedirs(log_dir)
    log_file = os.path.join(log_dir, "code_repair.log")

    level = logging.DEBUG if verbose else logging.INFO

    formatter = logging.Formatter("%(asctime)s [%(levelname)s] %(message)s")

    formatter.default_time_format = "%H:%M:%S"

    logger = logging.getLogger("CodeRepair")
    logger.setLevel(level)
    # 控制台输出
    ch = logging.StreamHandler()
    ch.setFormatter(formatter)
    logger.addHandler(ch)
    # 文件输出
    fh = logging.FileHandler(log_file)
    fh.setFormatter(formatter)
    logger.addHandler(fh)


@contextmanager
def cd(path: Path):
    """Sets the cwd within the context

    Args:
        path (Path): The path to the cwd

    Yields:
        None
    """

    origin = Path().absolute()
    try:
        os.chdir(path) # 将当前工作目录切换到 path 指定的目录
        yield # 先执行with,后执行yield和finally
    finally: # 完成后
        os.chdir(origin)


def tag(content: str, tag_name: str) -> str:
    """
    Args:
        content: 内容
        tag_name: 目标名称

    Returns:
        if content = NULL  =>   NULL
        if content != NULL =>   <tag_name>
                                content
                                </tag_name>
     """
    if not content:
        return content
    return f"\n<{tag_name}>\n{content}\n</{tag_name}>\n"


def generate_secure_string(length):
    characters = string.ascii_letters + string.digits  # a-zA-Z0-9
    return ''.join(secrets.choice(characters) for _ in range(length))


def find_files_with_str_in_name(folder_path: str | Path, search_str: str) -> List[str]:
    """
    遍历指定文件夹及其子目录中的所有文件，提取文件名（不包含扩展名），
    将其以 '_' 分割，若第一部分与指定前缀相同，则将该文件名添加到结果列表中。

    参数:
        folder_path (str or Path): 要搜索的文件夹路径。
        prefix (str): 用于匹配文件名分割后的第一个部分。

    返回:
        List[str]: 匹配到的文件名（不带扩展名）列表。

    抛出:
        FileNotFoundError: 如果提供的路径不存在或不是一个有效目录。
    """
    folder = Path(folder_path)
    if not folder.exists() or not folder.is_dir():
        raise FileNotFoundError(f"路径不存在或不是一个有效目录: {folder}")

    result: List[str] = []

    for root, _, files in os.walk(folder):
        for file in files:
            filename_without_ext = Path(file).stem
            parts = filename_without_ext.split("_")
            if parts and parts[0] == search_str:
                result.append(filename_without_ext)

    return result


def merge_and_deduplicate(list_of_lists: List[List[str]]) -> str:
    """
        将一个二维列表中的所有元素合并成一个字符串，并去除重复项。

        参数:
            list_of_lists (List[List[str]]): 包含多个子列表的二维列表，
                                              每个子列表包含字符串类型的元素，
                                              元素可能是单值（如 "1"）或范围（如 "1-2"）。
        返回:
            str: 由去重后的所有元素按排序顺序拼接而成的字符串，元素之间用逗号分隔。
        """
    unique_items = set()

    for sublist in list_of_lists:
        for item in sublist:
            unique_items.add(item)

    sorted_items = sorted(unique_items, key=lambda x: (len(x), x))
    result_string = ','.join(sorted_items)
    return result_string


def get_rules(options: Options) -> Dict[str, Dict[str, str | List[str]]]:
    """
    获取错误类型及修复建议
    :param options configuration
    :return: rules
    """
    try:
        with open(options.rules_path, "r", encoding="utf-8") as f:
            rules = json.load(f)
    except FileNotFoundError:
        logger.info(f"Failed to get rules!")
        raise FileNotFoundError(f"Failed to read file: {options.rules_path}")
    return rules



def get_file_and_method_json(options: Options) -> Dict[str, str] | None:
    """
    类属性及方法
    :param options configuration
    :return: file_and_method_json
    """
    if options.dataset_type == "java":
        file_and_method_path = f"{options.dataset_position}/file_and_method"
        try:
            with open(f"{file_and_method_path}/{options.sample}.json", "r", encoding="utf-8") as f:
                file_and_method_json = json.load(f)
        except FileNotFoundError:
            logger.info(f"Failed to retrieve classes, properties, and methods of non d4j functions!")
            raise FileNotFoundError(f"Failed to read file: {file_and_method_path}/{options.sample}.json")
        return file_and_method_json
    else:
        return None


def get_bug_dict(options: Options) -> Dict[str, Dict[str, str | int]] | None:
    if options.dataset_type == "java":
        try:
            with open(options.d4j_single_func, "r") as f:
                bug_dict = json.load(f)
        except FileNotFoundError:
            logger.info(f"Failed to get single function information!")
            raise FileNotFoundError(f"Failed to read file: {options.d4j_single_func}")
        return bug_dict
    else:
        return None


def get_bug_code(options: Options) -> str:
    """
    获取bug函数
    :param options:
    :return: bug code
    """
    code_path = f"{options.dataset_position}/bug_function"
    try:
        with open(f"{code_path}/{options.sample}.{options.dataset_type}","r") as f:
            code = f.read()
    except FileNotFoundError:
        logger.info(f"Failed to get bug code")
        raise FileNotFoundError(f"Failed to read file: {code_path}/{options.sample}.{options.dataset_type}")
    return code


def get_test_case(options: Options) -> str:
    test_case_path = f"{options.dataset_position}/test_case"
    if options.dataset_type == "java":
        # 获取所有测试失败的文件
        test_case_file = find_files_with_str_in_name(test_case_path, options.sample)
        try:
            test_cases = []
            for file_name in test_case_file:
                with open(f"{test_case_path}/{file_name}.{options.dataset_type}", "r") as f:
                    test_cases.append(f.read())
            test_case = "\n".join(test_cases)
        except FileNotFoundError:
            logger.info(f"Failed to get test case!")
            raise FileNotFoundError(f"Failed to read file: {test_case_path}/{str(test_case_file)}.{options.dataset_type}")
    else:
        try:
            with open(f"{test_case_path}/{options.sample}.txt", "r") as f:
                test_case = f.read()
        except FileNotFoundError:
            logger.info(f"Failed to get test case!")
            raise FileNotFoundError(f"Failed to read file: {test_case_path}/{options.sample}.txt")
    return test_case


def get_test_trace(options: Options) -> str:
    trace_path = f"{options.dataset_position}/trace_key_info"
    try:
        with open(f"{trace_path}/{options.sample}.txt", "r") as f:
            trace = f.read()
    except FileNotFoundError:
        logger.info(f"Failed to obtain stack information for the d4j function test function!")
        raise FileNotFoundError(f"Failed to read file: {trace_path}/{options.sample}")
    return trace



def get_pytest_info(options: Options) -> str:
    pytest_info_path = f"{options.dataset_position}/pytest_stdout"
    try:
        with open(f"{pytest_info_path}/{options.sample}.txt", "r") as f:
            pytest_info = f.read()
    except FileNotFoundError:
        logger.info(f"Failed to read file: {pytest_info_path}/{options.sample}")
        raise FileNotFoundError(f"Failed to read file: {pytest_info_path}/{options.sample}")
    return pytest_info


def count_tokens(text: str, model: str = "cl100k_base") -> int:
    """
    计算给定文本在 OpenAI 模型下的 token 数量。

    参数:
        text (str): 输入文本
        model (str): 所用模型对应的编码器，默认为 cl100k_base（适用于 GPT-3.5 Turbo 和 GPT-4）

    返回:
        int: token 数量
    """
    try:
        encoding = tiktoken.get_encoding(model)
    except Exception as e:
        raise ValueError(f"Model {model} not supported by tiktoken: {e}")

    tokens = encoding.encode(text)
    return len(tokens)


def split_string(s: str) -> List[str]:
    if ',' in s:
        return s.split(',')
    else:
        return [s]


def generate_repair_suggestion(error_types_list: List[str], error_dict: Dict[str, Dict[str, str | List[str]]], history = False):
    result_lines = []
    for idx, error_type in enumerate(error_types_list, start=1):
        if error_type in error_dict:
            repair_prompt = error_dict[error_type]["repair_prompt"]
        else:
            raise ValueError(f"{error_type} not in rules")
        if history:
            result_lines.append(repair_prompt)
        else:
            result_lines.append(f"{idx}:\n error type: {error_type}\n repair suggestion: {repair_prompt}")
    return "\n".join(result_lines)



def get_method_code(options: Options, method_list: List[str]) -> str:
    all_method_path = f"{options.dataset_position}/methods_code"
    try:
        with open(f"{all_method_path}/{options.sample}.json", "r") as f:
            all_method = json.load(f)
    except FileNotFoundError:
        logger.info(f"Failed to read file: {all_method_path}/{options.sample}")
        raise FileNotFoundError(f"Failed to read file: {all_method_path}/{options.sample}")
    context = ""
    for method in method_list:
        method_code = all_method.get(method,"")
        if method_code != "":
            context = context + "\n" + method_code
    return context


def construct_context(file_and_method_json: Dict[str, str | List[str]], method_code: str):
    class_name = file_and_method_json["ClassName"]
    fields = file_and_method_json["Fields"]

    information = (f"- ClassName: {class_name}\n"
                    + f"- Attributes: {str(fields)}\n"
                    + f"- Methods:\n{method_code}")
    return information


def save_conversion_and_result(options: Options,
                                candidate_responses: List[str],
                                candidate_answers: List[str],
                                prompt: str,
                                index: int,
                                conversion_file_name: str,
                                res_file_name: str,
                                ) -> None:
    """
    保存候选人的响应到对话历史文件，并保存bug类型结果到结果目录。
    :param options:
    :param candidate_responses: 候选人响应的列表
    :param candidate_answers: 候选人答案的列表
    :param prompt: bug类型提示信息（用户输入的内容）
    :param index: 当前样本的索引
    :param conversion_file_name:
    :param res_file_name:
    """
    prompt_path = f"{options.work_dir}/conversation_history"
    os.makedirs(prompt_path, exist_ok=True)

    workspace_res_path = f"{options.work_res_dir}"
    os.makedirs(workspace_res_path, exist_ok=True)

    result_path = f"{options.res_record_dir}/fix_num-{index}"
    os.makedirs(result_path, exist_ok=True)

    for candidate_idx, (response, answer) in enumerate(zip(candidate_responses, candidate_answers)):
        # save conversion
        with open(f"{prompt_path}/fix_num-{index}_{conversion_file_name}.txt", "a") as f:
            contents = (
                f"=============================== Candidate {candidate_idx + 1} ===============================\n\n"
                f"{prompt}\n"
                "assistant:\n"
                f"{response}\n\n\n"
            )
            f.write(contents)

        # save in workspace
        with open(f"{workspace_res_path}/fix_num-{index}_{res_file_name}.txt", "a") as f:
            f.write(
                f"=============================== Candidate {candidate_idx + 1} ===============================\n\n")
            f.write(str(answer)+"\n\n")

        # save in result
        with open(f"{result_path}/candidate-{candidate_idx}_{res_file_name}.txt", "w") as f:
            f.write(str(answer))



def save_history_and_result(options: Options,
                                candidate_responses: List[str],
                                candidate_answers: List[str],
                                prompt: List[str],
                                index: int,
                                conversion_file_name: str,
                                res_file_name: str,
                                ) -> None:
    """
    保存候选人的响应到对话历史文件，并保存bug类型结果到结果目录。
    :param options:
    :param candidate_responses: 候选人响应的列表
    :param candidate_answers: 候选人答案的列表
    :param prompt: bug类型提示信息（用户输入的内容）
    :param index: 当前样本的索引
    :param conversion_file_name:
    :param res_file_name:
    """
    prompt_path = f"{options.work_dir}/conversation_history"
    os.makedirs(prompt_path, exist_ok=True)

    workspace_res_path = f"{options.work_res_dir}"
    os.makedirs(workspace_res_path, exist_ok=True)

    result_path = f"{options.res_record_dir}/fix_num-{index}"
    os.makedirs(result_path, exist_ok=True)

    for candidate_idx, (response, answer) in enumerate(zip(candidate_responses, candidate_answers)):
        # save conversion
        with open(f"{prompt_path}/fix_num-{index}_{conversion_file_name}.txt", "a") as f:
            contents = (
                f"=============================== Candidate {candidate_idx + 1} ===============================\n\n"
                f"{prompt[candidate_idx]}\n"
                "assistant:\n"
                f"{response}\n\n\n"
            )
            f.write(contents)

        # save in workspace
        with open(f"{workspace_res_path}/fix_num-{index}_{res_file_name}.txt", "a") as f:
            f.write(
                f"\n\n=============================== Candidate {candidate_idx + 1} ===============================\n\n")
            f.write(str(answer))

        # save in result
        with open(f"{result_path}/candidate-{candidate_idx}_{res_file_name}.txt", "w") as f:
            f.write(str(answer))


def get_unified_diff(source, mutant):
    """
    比较两个文本字符串并返回它们的统一格式差异（unified diff）。
    参数:
        source (str): 原始文本内容。
        mutant (str): 修改后的文本内容。
    返回:
        str: 表示两个文本之间差异的统一 diff 格式字符串。
    示例:
        ---
        +++
        @@ -1,3 +1,3 @@
         line1
        -line2
        +changed_line2
         line3
    """
    output = ""
    for line in unified_diff(source.split('\n'), mutant.split('\n'), lineterm=''):
        output += line + "\n"
    return output


def get_patch_list(patch: str) -> List[str]:
    temp_path = "/tmp/patch"
    os.makedirs(temp_path, exist_ok=True)
    try:
        with open(f"{temp_path}/patch.java","w") as f:
            f.write(patch)
        with open(f"{temp_path}/patch.java","r") as f:
            patch_list = f.readlines()
    except FileNotFoundError:
        raise FileNotFoundError("get patch list failed!")
    subprocess.run(["rm", "-rf", temp_path], check=True)
    return patch_list


def save_patch_in_record(options: Options, patches: List[str], fix_idx: int) -> None:
    if not patches:
        logger.info(f"no patches!")
        return
    patch_path = f"{options.res_record_dir}/fix_num-{fix_idx}"
    os.makedirs(patch_path, exist_ok=True)
    for index, patche in enumerate(patches):
        with open(f"{patch_path}/candidate-{index}_patch.{options.dataset_type}","w") as f:
            f.write(patche)
    logger.info(f"Successfully saved repair results.")


def save_diff(options: Options, diff: str, fix_idx: int, candidate_idx: int) -> None:
    if not diff:
        logger.info(f"no diff!")
        return
    diff_path = f"{options.res_record_dir}/fix_num-{fix_idx}"
    os.makedirs(diff_path, exist_ok=True)
    with open(f"{diff_path}/candidate-{candidate_idx}_diff.txt","w") as f:
        f.write(diff)
    logger.info(f"Successfully saved diff.")


def save_reason(options: Options, reason: str, fix_idx: int, candidate_idx: int) -> None:
    if not reason:
        logger.info(f"no reason!")
        return
    reason_path = f"{options.res_record_dir}/fix_num-{fix_idx}"
    os.makedirs(reason_path, exist_ok=True)
    with open(f"{reason_path}/candidate-{candidate_idx}_reason.txt","w") as f:
        f.write(reason)
    logger.info(f"Successfully saved reason.")



def save_patch_in_result(options: Options, patch: str, fix_idx: int, candidate_idx: int, is_success: bool) -> None:
    if not patch:
        logger.info(f"No patch")
        return
    if is_success:
        patch_path = f"{options.res_result_dir}/success/{options.sample}"
    else:
        patch_path = f"{options.res_result_dir}/fail/{options.sample}"
    os.makedirs(patch_path, exist_ok=True)
    with open(f"{patch_path}/fix_num-{fix_idx}_candidate-{candidate_idx}_{options.sample}.{options.dataset_type}","w") as f:
        f.write(patch)
    logger.info(f"Successfully saved repair results.")
