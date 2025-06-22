"""
Bug 修复模块，使用 LLM 进行补丁生成。
"""
import copy
import logging
import llms
from typing import List, Dict, Tuple

from llms import QueryEngine
from prompt_templates import bug_detection_prompt, bug_classification_prompt, bug_analysis_prompt, bug_repair_prompt
from settings import Options
from utils import merge_and_deduplicate, save_conversion_and_result, count_tokens, save_history_and_result

logger = logging.getLogger("CodeRepair")


class Repair:
    def __init__(
        self,
        options: Options,
        query_engine: QueryEngine,
        error_types: List[str],
        rules: Dict[str, Dict[str, str | List[str]]],
        file_and_method_json: Dict[str, str | List[str]] = None,
    ) -> None:
        self.options = options
        self.query_engine = query_engine
        self.error_types = error_types
        self.rules = rules
        self.file_and_method_json = file_and_method_json

    def get_bug_position(self, index: int) -> (str, List[List[Tuple[str,str]]]):
        task_uid = "get_bug_position"
        logger.info(f"======= Starting to {task_uid} =======")
        overflow_state: int = 0
        while True:
            bug_position_prompt = bug_detection_prompt(self.options, overflow_state)
            tokens_num = count_tokens(str(bug_position_prompt))
            if tokens_num >= self.options.max_tokens * 0.8:
                logger.info(f"Error: The current number of tokens {tokens_num} exceeds the maximum number of tokens for {self.options.model}!")
                overflow_state += 1
            else:
                break
            if overflow_state >= 3:
                logger.info("Error: There are too many tokens for prompt, unable to obtain answers!")
                logger.info("Error: The code repair failed this time!")
                exit(1)

        candidate_responses: list = []
        candidate_answers: list = []

        histories = []
        output_tokens_list = []

        logger.info(f"Get bug location by {self.options.model}")
        for i in range(self.options.llm_retry_num):
            # 从 llms 获取结果
            responses, answers = self.query_engine.generate_code(bug_position_prompt,
                                                            self.options.task_candidate_num,
                                                            self.options.initial_temperature,
                                                            task=task_uid)
            for response, answer in zip(responses, answers):
                if response != "" and answer != []:
                    candidate_responses.append(response)
                    candidate_answers.append(answer)
                    output_tokens_list.append(count_tokens(str(response)))
                    if self.options.conversation:
                        candidate_history = [(llms.USER, str(bug_position_prompt)), (llms.ASSISTANT, response)]
                        histories.append(candidate_history)
            if len(candidate_answers) > 0:
                break
            logger.info("No answer, retry!")

        if len(candidate_answers) == 0:
            logger.info("Attempt to locate the bug location failed, no answer.")
            raise RuntimeError("Attempt to locate the bug location failed!")

        output_tokens_num = sum(output_tokens_list)
        input_tokens_num = tokens_num * len(output_tokens_list)
        logger.info(f"total_input_tokens: {input_tokens_num}")
        logger.info(f"total_output_tokens: {output_tokens_num}")

        # save candidate
        save_conversion_and_result(self.options,
                                    candidate_responses,
                                    candidate_answers,
                                    str(bug_position_prompt),
                                    index,
                                    conversion_file_name=task_uid,
                                    res_file_name=task_uid)

        # merge result
        bug_position = merge_and_deduplicate(candidate_answers)

        logger.info("======= Successfully get bug location =======")
        return bug_position, histories


    def get_bug_type(self, index: int, bug_position: str, histories: List[List[Tuple[str,str]]]) -> (str, List[List[Tuple[str,str]]]):
        task_uid = "get_bug_type"
        logger.info(f"======= Starting to {task_uid} =======")
        overflow_state: int = 0
        while True:
            bug_type_prompts = bug_classification_prompt(self.options, overflow_state, self.error_types, bug_position, histories)
            prompts_str_list = [self.query_engine.stringify_prompt(prompts) for prompts in bug_type_prompts]
            max_len_prompt = max(prompts_str_list, key=len)
            tokens_num = count_tokens(max_len_prompt)
            if tokens_num >= self.options.max_tokens * 0.8:
                logger.info(
                    f"Error: The current number of tokens {tokens_num} exceeds the maximum number of tokens for {self.options.model}!")
                overflow_state += 1
            else:
                break
            if overflow_state >= 3:
                logger.info("Error: There are too many tokens for prompt, unable to obtain answers!")
                logger.info("Error: The code repair failed this time!")
                exit(1)

        candidate_responses: list = []
        candidate_answers: list = []
        output_tokens_list = []
        new_histories = copy.deepcopy(histories)
        logger.info(f"Get bug type by {self.options.model}")

        if histories:   # histories != []  => conversion
            for i, history in enumerate(histories):
                # 从 llms 获取结果
                responses, answers = self.query_engine.generate_code(bug_type_prompts[i],
                                                                    1,
                                                                    self.options.initial_temperature + (i  * 0.4),
                                                                    task=task_uid)
                response = responses[0]
                answer = answers[0]
                if response != "" and answer != []:
                    candidate_responses.append(response)
                    candidate_answers.append(answer)
                    output_tokens_list.append(count_tokens(str(response)))
                    new_histories[i].append((llms.USER, str(bug_type_prompts[i])))
                    new_histories[i].append((llms.ASSISTANT, response))
        else:
            bug_type_prompt = bug_type_prompts[0]
            for i in range(self.options.llm_retry_num):
                # 从 llms 获取结果
                responses, answers = self.query_engine.generate_code(bug_type_prompt,
                                                                self.options.task_candidate_num,
                                                                self.options.initial_temperature,
                                                                task=task_uid)
                for response, answer in zip(responses, answers):
                    if response != "" and answer != []:
                        candidate_responses.append(response)
                        candidate_answers.append(answer)
                        output_tokens_list.append(count_tokens(str(response)))
                        if self.options.conversation:
                            candidate_history = [(llms.USER, str(bug_type_prompt)), (llms.ASSISTANT, response)]
                            new_histories.append(candidate_history)
                if len(candidate_answers) > 0:
                    break
                logger.info("No answer, retry!")

        if len(candidate_answers) == 0:
            logger.info("Attempt to judge the bug type failed, no answer.")
            raise RuntimeError("Attempt to judge the bug type failed!")

        output_tokens_num = sum(output_tokens_list)
        input_tokens_num = tokens_num * len(output_tokens_list)
        logger.info(f"total_input_tokens: {input_tokens_num}")
        logger.info(f"total_output_tokens: {output_tokens_num}")

        # save candidate
        if histories:
            save_history_and_result(self.options,
                                        candidate_responses,
                                        candidate_answers,
                                        [self.query_engine.stringify_prompt(bug_type_prompt) for bug_type_prompt in bug_type_prompts],
                                        index,
                                        conversion_file_name=task_uid,
                                        res_file_name=task_uid)
        else:
            save_conversion_and_result(self.options,
                                        candidate_responses,
                                        candidate_answers,
                                        self.query_engine.stringify_prompt(bug_type_prompts[0]),
                                        index,
                                        conversion_file_name=task_uid,
                                        res_file_name=task_uid)

        bug_type = merge_and_deduplicate(candidate_answers)

        logger.info("======= Successfully get bug type =======")
        return bug_type, new_histories


    def get_bug_context(self, index: int, new_histories: List[List[Tuple[str,str]]]) -> (str, List[List[Tuple[str,str]]]):
        task_uid = "get_bug_context"
        logger.info(f"======= Starting to {task_uid} =======")
        overflow_state: int = 0
        while True:
            bug_context_prompts = bug_analysis_prompt(self.options, overflow_state, self.file_and_method_json, new_histories)
            prompts_str_list = [self.query_engine.stringify_prompt(prompts) for prompts in bug_context_prompts]
            max_len_prompt = max(prompts_str_list, key=len)
            tokens_num = count_tokens(max_len_prompt)
            if tokens_num >= self.options.max_tokens * 0.8:
                logger.info(
                    f"Error: The current number of tokens {tokens_num} exceeds the maximum number of tokens for {self.options.model}!")
                overflow_state += 1
            else:
                break
            if overflow_state >= 3:
                logger.info("Error: There are too many tokens for prompt, unable to obtain answers!")
                logger.info("Error: The code repair failed this time!")
                exit(1)

        candidate_responses: list = []
        candidate_answers: list = []
        output_tokens_list = []
        last_histories = copy.deepcopy(new_histories)

        if new_histories:   # histories != []  => conversion
            for i, history in enumerate(new_histories):
                # 从 llms 获取结果
                responses, answers = self.query_engine.generate_code(bug_context_prompts[i],
                                                                    1,
                                                                    self.options.initial_temperature + (i * 0.4),
                                                                    task_uid,
                                                                    self.file_and_method_json)
                response = responses[0]
                answer = answers[0]
                candidate_responses.append(response)
                candidate_answers.append(answer)
                output_tokens_list.append(count_tokens(str(response)))
                last_histories[i].append((llms.USER, str(bug_context_prompts[i])))
                last_histories[i].append((llms.ASSISTANT, response))
        else:
            bug_context_prompt = bug_context_prompts[0]
            logger.info(f"Get bug context by {self.options.model}")
            responses, answers = self.query_engine.generate_code(bug_context_prompt,
                                                                self.options.task_candidate_num,
                                                                self.options.initial_temperature,
                                                                task_uid,
                                                                self.file_and_method_json)
            for response, answer in zip(responses, answers):
                candidate_responses.append(response)
                candidate_answers.append(answer)
                output_tokens_list.append(count_tokens(str(response)))
                if self.options.conversation:
                    candidate_history = [(llms.USER, str(bug_context_prompt)), (llms.ASSISTANT, response)]
                    last_histories.append(candidate_history)

        output_tokens_num = sum(output_tokens_list)
        input_tokens_num = tokens_num * len(output_tokens_list)
        logger.info(f"total_input_tokens: {input_tokens_num}")
        logger.info(f"total_output_tokens: {output_tokens_num}")

        # save candidate
        if  new_histories:
            save_history_and_result(self.options,
                                    candidate_responses,
                                    candidate_answers,
                                    [self.query_engine.stringify_prompt(bug_context_prompt) for bug_context_prompt in bug_context_prompts],
                                    index,
                                    conversion_file_name=task_uid,
                                    res_file_name=task_uid)
        else:
            save_conversion_and_result(self.options,
                                        candidate_responses,
                                        candidate_answers,
                                        self.query_engine.stringify_prompt(bug_context_prompts[0]),
                                        index,
                                        conversion_file_name=task_uid,
                                        res_file_name=task_uid)

        bug_context = merge_and_deduplicate(answers)

        logger.info("======= Successfully get bug context =======")
        return bug_context, last_histories

    def get_bug_patch(self,
                    index: int,
                    bug_position: str,
                    bug_type: str,
                    bug_context:str,
                    last_history: List[List[Tuple[str, str]]]
                    ) -> List[str]:
        task_uid = "get_bug_patch"
        logger.info(f"======= Starting to {task_uid} =======")
        overflow_state: int = 0
        while True:
            bug_fix_prompts = bug_repair_prompt(self.options, overflow_state, self.rules, bug_position, bug_type, bug_context, self.file_and_method_json, last_history)
            prompts_str_list = [self.query_engine.stringify_prompt(prompts) for prompts in bug_fix_prompts]
            max_len_prompt = max(prompts_str_list, key=len)
            tokens_num = count_tokens(str(max_len_prompt))
            if tokens_num >= self.options.max_tokens * 0.6:
                logger.info(f"Error: The current number of tokens {tokens_num} exceeds the maximum number of tokens for {self.options.model}!")
                overflow_state += 1
            else:
                break
            if overflow_state > 3:
                logger.info("Error: There are too many tokens for prompt, unable to obtain answers!")
                logger.info("Error: The code repair failed this time!")
                exit(1)

        candidate_responses: list = []
        candidate_answers: list = []
        output_tokens_list = []

        logger.info(f"Get bug patch by {self.options.model}")

        if last_history:
            for i, history in enumerate(last_history):
                # 从 llms 获取结果
                responses, answers = self.query_engine.generate_code(bug_fix_prompts[i],
                                                                    1,
                                                                    self.options.initial_temperature + (i * 0.4),
                                                                    task=task_uid)
                response = responses[0]
                answer = answers[0]
                if response != "" and answer != "":
                    candidate_responses.append(response)
                    candidate_answers.append(answer)
                    output_tokens_list.append(count_tokens(str(response)))
        else:
            bug_fix_prompt = bug_fix_prompts[0]
            for i in range(self.options.llm_retry_num):
                # 从 llms 获取结果
                responses, answers = self.query_engine.generate_code(bug_fix_prompt,
                                                                self.options.repair_candidate_num,
                                                                self.options.initial_temperature,
                                                                task=task_uid)
                for response, answer in zip(responses, answers):
                    if response != "" and answer != "":
                        candidate_responses.append(response)
                        candidate_answers.append(answer)
                        output_tokens_list.append(count_tokens(str(response)))
                if len(candidate_responses) > 0:
                    break
                logger.info("No answer, retry!")

        if len(candidate_answers) == 0:
            logger.info("Attempt to get the bug patch, no answer.")
            raise RuntimeError("Attempt to get the bug patch, no answer.")

        output_tokens_num = sum(output_tokens_list)
        input_tokens_num = tokens_num * len(output_tokens_list)
        logger.info(f"total_input_tokens: {input_tokens_num}")
        logger.info(f"total_output_tokens: {output_tokens_num}")

        # save candidate
        if last_history:
            save_history_and_result(self.options,
                                    candidate_responses,
                                    candidate_answers,
                                    [self.query_engine.stringify_prompt(bug_fix_prompt) for bug_fix_prompt in bug_fix_prompts],
                                    index,
                                    conversion_file_name=task_uid,
                                    res_file_name=task_uid)
        else:
            save_conversion_and_result(self.options,
                                        candidate_responses,
                                        candidate_answers,
                                        self.query_engine.stringify_prompt(bug_fix_prompts[0]),
                                        index,
                                        conversion_file_name=task_uid,
                                        res_file_name=task_uid)

        logger.info("======= Successfully get bug patch =======")
        return candidate_answers