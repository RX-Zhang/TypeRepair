"""
Prompt 模板模块，为不同阶段生成 LLM 提示词。
"""

import logging
from settings import Options
from utils import tag, get_bug_code, get_test_case, get_test_trace, split_string, \
    generate_repair_suggestion, get_method_code, construct_context, get_pytest_info
from dataclasses import dataclass, field
from typing import Tuple, List, Dict

logger = logging.getLogger("CodeRepair")

@dataclass
class Prompt:
    """
    提示的结构化表示
    Args:
        history (List[Tuple[str, str]]): 由 role和 content列表组成的对话
    """
    # 静态共享变量
    instruction: str = ""
    context: str = ""
    test_case: str = ""
    extra_info: str = ""
    code: str = ""
    constraint: str = ""

    history: List[Tuple[str, str]] = field(default_factory=list)  # todo

    def __str__(self) -> str:
        parts = []
        if self.instruction:
            parts.append(f"{self.instruction}\n")
        if self.context:
            parts.append(f"{self.context}\n")
        if self.test_case:
            parts.append(f"{self.test_case}\n")
        if self.extra_info:
            parts.append(f"{self.extra_info}\n")
        if self.code:
            parts.append(f"{self.code}\n")
        if self.constraint:
            parts.append(f"{self.constraint}\n")
        return "\n".join(parts)



def bug_detection_prompt(options: Options, overflow_state: int) -> Prompt:
    logger.info("Start retrieving bugs localization prompt template.")
    # code
    code = get_bug_code(options)
    # test_case
    test_case = get_test_case(options)

    output_format = ("If it's a single-line error: Example: 3\n"
                    + "If it's a multi-line error: Example: 1-3\n"
                    + "If there are multiple errors, separate them with \',\': Example: 3, 4-7")

    if options.dataset_type == "java":
        # trace
        trace = get_test_trace(options)

        prompt = Prompt(
            instruction = ("### Role:\n"
                        + "You are an experienced software engineer skilled at finding bugs in code.\n\n"
                        + "### Task:\n"
                        + "The code within the <code> tag contains bugs. Please try to locate them.\n"),
            test_case = ("### Test Case:\n"
                        + "Here are some failed test functions enclosed in the <test_case> tag."
                        + tag(test_case,"test_case")) if overflow_state < 2 else "",
            extra_info = ("### Error Trace:\n"
                        + "Here is the stack trace information generated when the failed test functions were executed, enclosed in the <trace> tag."
                        + tag(trace,"trace")) if overflow_state < 1 else "",
            code = ("### Java Code(Java 1.8):"
                        + tag(code,"code")),
            constraint = ("### Output Format:\n"
                        + "Just provide the line numbers in <code> where the bugs are located. No extra output is needed.\n"
                        + tag(output_format,"format"))
        )
    else:
        # pytest_info
        pytest_info = get_pytest_info(options)

        prompt = Prompt(
            instruction=("### Role:\n"
                        + "You are an experienced software engineer skilled at finding bugs in code.\n\n"
                        + "### Task:\n"
                        + "The code inside the <code> tags contains bugs. Please try to locate them."),
            test_case=("### Test Case:\n"
                        + "Contained within the <test_case> tag are test functions or some test cases."
                        + tag(test_case, "test_case")) if overflow_state < 2 else "",
            extra_info=("### Test Error:\n"
                        + "The content within the <pytest_info> tag contains the output from pytest tests, which you can use as a reference."
                        + tag(pytest_info, "pytest_info")) if overflow_state < 1 else "",
            code=("### Python Code:"
                        + tag(code, "code")),
            constraint=("### Output Format:\n"
                        + "Just provide the line numbers in <code> where the bugs are located. No extra output is needed.\n"
                        + tag(output_format, "format"))
        )
    logger.info("Successfully retrieved bugs localization prompt template.")
    return prompt


def bug_classification_prompt(options: Options,
                            overflow_state: int,
                            error_types: List[str],
                            bug_position: str,
                            histories: List[List[Tuple[str,str]]]) -> List[Prompt]:
    logger.info("Start retrieving bug classification prompt template.")
    if histories and overflow_state < 1:  # histories != []  =>  1. have bug_position   2. conversion
        prompt_list = []
        for history in histories:
            prompt = Prompt(
                instruction=("Please identify which type of error it belongs to from the list in the <error_types> tag."
                            + tag(str(error_types), "error_types")),
                constraint=("### Output Format:\n"
                            + "Just tell me the error types. No other extra output is needed."),
                history=history)
            prompt_list.append(prompt)
        return prompt_list
    else:
        # code
        code = get_bug_code(options)
        # test_case
        test_case = get_test_case(options)

        if bug_position == "":
            task_description = "The code within the <code> tag contains bugs. Please identify which type of error it belongs to from the list in the <error_types> tag."
        else:
            task_description = f"The code enclosed in the <code> tag contains errors, the lines that may cause the error are: {bug_position}. Please determine which error type it belongs to from the <error_types> tag."

        if options.dataset_type == "java":
            # trace
            trace = get_test_trace(options)

            prompt = Prompt(
                instruction = ("### Role:\n"
                            + "You are an experienced software engineer skilled in identifying error types in code.\n\n"
                            + "### Task:\n"
                            + task_description
                            + tag(str(error_types), "error_types")),
                test_case = ("### Test Case:\n"
                            + "Here are some failed test functions enclosed in the <test_case> tag."
                            + tag(test_case,"test_case")) if overflow_state < 2 else "",
                extra_info = ("### Error Trace:\n"
                            + "Here is the stack trace information generated when the failed test functions were executed, enclosed in the <trace> tag."
                            + tag(trace,"trace")) if overflow_state < 1 else "",
                code=("### Java Code(Java 1.8):"
                            + tag(code, "code")),
                constraint = ("### Output Format:\n"
                            + "Just tell me the error types. No other extra output is needed.")
            )
        else:
            # pytest_info
            pytest_info = get_pytest_info(options)

            prompt = Prompt(
                instruction=("### Role:\n"
                            + "You are an experienced software engineer skilled in identifying error types in code.\n\n"
                            + "### Task:\n"
                            + task_description
                            + tag(str(error_types), "error_types")),
                test_case=("### Test Case:\n"
                            + "Contained within the <test_case> tag are test functions or some test cases."
                            + tag(test_case, "test_case")) if overflow_state < 2 else "",
                extra_info=("### Test Error:\n"
                            + "The content within the <pytest_info> tag contains the output from pytest tests, which you can use as a reference."
                            + tag(pytest_info, "pytest_info")) if overflow_state < 1 else "",
                code=("### Python Code:"
                            + tag(code, "code")),
                constraint=("### Output Format:\n"
                            + "Just tell me the error types. No other extra output is needed.\n")
            )
            logger.info("Successfully retrieved bug classification prompt template.")
        return [prompt]


def bug_analysis_prompt(options: Options, overflow_state: int, file_and_method_json: Dict[str, str | List[str]], histories: List[List[Tuple[str,str]]]) -> List[Prompt]:
    logger.info("Start retrieving bug context prompt template.")

    class_name = file_and_method_json.get("ClassName", "No class")
    fields = file_and_method_json.get("Fields", "No")
    methods = file_and_method_json.get("Methods", "No")
    information = (f"- ClassName: {class_name}\n"
                    + f"- Field: {str(fields)}\n"
                    + f"- Methods: {str(methods)}")

    if histories and overflow_state < 1:  # histories != []  =>  1. have before info   2. conversion
        task_description = (f"This code is part of the {class_name} class. "
                            + "Information about this class is provided in the <information> tag. "
                            + "Please select methods from the \'Methods\' list that can help fix the bugs.")
        prompt_list = []
        for history in histories:
            prompt = Prompt(
                instruction=(task_description
                            + tag(information,"information")),
                constraint=("### Output Format:\n"
                            + "Just tell me which methods in \'Methods\' are helpful for fixing the bugs. No other extra output is needed."),
                history=history)
            prompt_list.append(prompt)
        return prompt_list
    else:
        task_description = (f"The code within the <code> tag contains bugs. This code is part of the {class_name} class. "
                            + "Information about this class is provided in the <information> tag. "
                            + "Please select methods from the \'Methods\' list that can help fix the bugs.")
        # code
        code = get_bug_code(options)
        # test_case
        test_case = get_test_case(options)
        # trace
        trace = get_test_trace(options)

        prompt = Prompt(
            instruction=("### Role:\n"
                        + "You are an experienced software engineer skilled in analyzing code structure.\n\n"
                        + "### Task:\n"
                        + task_description
                        + tag(information,"information")),
            test_case=("### Test Case:\n"
                        + "Here are some failed test functions enclosed in the <test_case> tag."
                        + tag(test_case, "test_case")) if overflow_state < 2 else "",
            extra_info=("### Error Trace:\n"
                        + "Here is the stack trace information generated when the failed test functions were executed, enclosed in the <trace> tag."
                        + tag(trace, "trace")) if overflow_state < 1 else "",
            code=("### Python Code:"
                        + tag(code, "code")),
            constraint=("### Output Format:\n"
                        + "Just tell me which methods in \'Methods\' are helpful for fixing the bugs. No other extra output is needed.")
        )
        logger.info("Successfully retrieved bug context prompt template")
        return [prompt]



def bug_repair_prompt(options: Options,
                    overflow_state: int,
                    rules: Dict[str, Dict[str, str | List[str]]],
                    bug_position: str,
                    bug_type: str,
                    bug_context: str,
                    file_and_method_json: Dict[str, str | List[str]],
                    last_history: List[List[Tuple[str, str]]]
                    ) -> List[Prompt]:
    logger.info("Start retrieving bug fix prompt template.")
    if last_history and overflow_state < 1:
        prompt_list = []

        # context
        if bug_context != "":
            method_list = split_string(bug_context)
            method_code = get_method_code(options, method_list)
            context = construct_context(file_and_method_json, method_code)
        else:
            context = ""

        # repair suggestion
        if bug_type != "":
            bugs_type_list = split_string(bug_type)
            repair_suggestion = generate_repair_suggestion(bugs_type_list, rules, history=True)
        else:
            repair_suggestion = ""

        if options.dataset_type == "java":
            output_format = "```java\n{code}\n```"

            for history in last_history:
                prompt = Prompt(
                    instruction=("Please refer to the information above to fix the code.\n"
                                + repair_suggestion),
                    context=("### Context:\n"
                            + "The following content enclosed in the <context> tag describes the class to which the code belongs, its properties, and relevant class methods that may help with the bug fixing process."
                            + tag(context, "context")),
                    constraint=("### Output Format:\n"
                                + "Please provide only the complete repaired version of the code inside the <code> tag. No other extra output is needed.\n"
                                + output_format),
                    history=history)
                prompt_list.append(prompt)
            return prompt_list

        else:
            output_format = "```python\n{code}\n```"
            for history in last_history:
                prompt = Prompt(
                    instruction=("Please refer to the information above to fix the code.\n"
                                + repair_suggestion),
                    constraint=("### Output Format:\n"
                                + "Please provide only the complete repaired version of the code inside the <code> tag. No other extra output is needed.\n"
                                + output_format),
                    history=history)
                prompt_list.append(prompt)
            return prompt_list
    else:
        # code
        code = get_bug_code(options)
        # test_case
        test_case = get_test_case(options)

        # repair_suggestion
        if bug_type != "":
            bugs_type_list = split_string(bug_type)
            repair_suggestion = generate_repair_suggestion(bugs_type_list, rules)
        else:
            repair_suggestion = ""

        # context
        if bug_context != "":
            method_list = split_string(bug_context)
            method_code = get_method_code(options, method_list)
            context = construct_context(file_and_method_json, method_code)
        else:
            context = ""

        bug_position = bug_position if bug_position != "" else "No"
        repair_suggestion = repair_suggestion if repair_suggestion != "" else "No"

        # additional bug repair information
        bug_info = (f"- Lines where bugs may exist: {bug_position}\n"
                    f"- Possible types of errors and corresponding repair suggestions:\n{repair_suggestion}")

        if options.dataset_type == "java":
            # trace
            trace = get_test_trace(options)

            output_format = ("```java\n"
                            "{code}\n"
                            +"```")

            prompt = Prompt(
                instruction=("### Role:\n"
                            + "You are an experienced software engineer specializing in repairing bugs in code.\n\n"
                            + "### Task:\n"
                            + "The code within the <code> tag contains bugs, please attempt to repair it.\n\n"
                            + "### Bug Information:\n"
                            + "The content enclosed in the <bug_info> tag provides information about the bugs and suggestions for fixing them. Please refer to this information when making repairs."
                            + tag(bug_info,"bug_info")),
                context = ("### Context:\n"
                            + "The following content enclosed in the <context> tag describes the class to which the code belongs, its properties, and relevant class methods that may help with the bug fixing process."
                            + tag(context, "context")) if bug_context != "" and overflow_state < 3 else "",
                test_case=("### Test Case:\n"
                            + "Here are some failed test functions enclosed in the <test_case> tag."
                            + tag(test_case, "test_case")) if overflow_state < 2 else "",
                extra_info=("### Error Trace:\n"
                            + "Here is the stack trace information generated when the failed test functions were executed, enclosed in the <trace> tag."
                            + tag(trace, "trace")) if overflow_state < 1 else "",
                code=("### Java Code(Java 1.8):"
                    + tag(code, "code")),
                constraint=("### Output Format:\n"
                            + "Please provide only the complete repaired version of the code inside the <code> tag. No other extra output is needed.\n"
                            + output_format)
            )

        else:
            # pytest_info
            pytest_info = get_pytest_info(options)

            output_format = ("```python\n"
                            "{code}\n"
                            +"```")

            prompt = Prompt(
                instruction=("### Role:\n"
                            + "You are an experienced software engineer specializing in repairing bugs in code.\n\n"
                            + "### Task:\n"
                            + "The code within the <code> tag contains bugs, please attempt to repair it.\n"
                            + "### Bug Information:\n"
                            + "The content enclosed in the <bug_info> tag provides information about the bugs and suggestions for fixing them. Please refer to this information when making repairs."
                            + tag(bug_info, "bug_info")),
                test_case=("### Test Case:\n"
                            + "Contained within the <test_case> tag are test functions or some test cases."
                            + tag(test_case, "test_case")) if overflow_state < 2 else "",
                extra_info=("### Test Error:\n"
                            + "The content within the <pytest_info> tag contains the output from pytest tests, which you can use as a reference."
                            + tag(pytest_info, "pytest_info")) if overflow_state < 1 else "",
                code=("### Python Code:"
                    + tag(code, "code")),
                constraint=("### Output Format:\n"
                            + "Please provide only the complete repaired version of the code inside the <code> tag. No other extra output is needed.\n"
                            + output_format)
            )
        logger.info("Successfully retrieved bug fix prompt template.")
        return [prompt]





