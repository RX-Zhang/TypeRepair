"""
#处理正确格式化提示不同LLM的代码
"""
import re
import logging
import settings

from abc import abstractmethod
from prompt_templates import Prompt
import json
import requests
import time

from typing import Any, List, Dict, Tuple, Union

from overrides import override
from tenacity import (
    retry,
    wait_random_exponential,
    stop_after_delay,
    retry_if_exception_type,
)

logger = logging.getLogger("CodeRepair")

USER = "USER"
ASSISTANT = "ASSISTANT"

class QueryError(Exception):
    """
    A wrapper around all sorts of errors thrown by LLMs
    """
    pass

class QueryEngine:
    def __init__(self, data_type: str, error_types: List[str]) -> None:
        self.data_type = data_type
        self.error_types = error_types

    @abstractmethod # 抽象方法，用于执行原始的模型查询操作。
    def raw_query(
        self,
        prompt: Union[str, Prompt],
        num_responses: int,
        init_model_temperature: float,
    ) -> List[str]: ...

    @retry(  # 实现函数执行时的自动重试机制，直到满足指定的条件或达到最大重试次数
        reraise=True,
        retry=retry_if_exception_type(QueryError),
        wait=wait_random_exponential(multiplier=1, max=60),
        stop=stop_after_delay(300),
    )
    def query(
        self,
        prompt: Prompt,
        num_responses: int,
        init_model_temperature: float = 0,
    ) -> List[str]:
        return self.raw_query(prompt, num_responses, init_model_temperature)

    def stringify_prompt(self, prompt: Prompt) -> str:
        """
        将 Prompt 对象按特定格式转为字符串
        """
        messages = self.messages(prompt) # 获取信息 list[Dict(role:content,role:content)]
        prompt_str = ""
        for message in messages:
            role = message["role"]
            content = message["content"]
            prompt_str += f"{role}:\n\n{content}\n\n"
        return prompt_str

    def generate_code(
        self,
        prompt: Prompt,
        num_responses: int,
        init_model_temperature: float,
        task: str,
        file_and_method_json = None,
    ) -> Any:
        """
        使用 llm生成代码
        Args:
            prompt: 提示信息
            num_responses: 生成个数
            init_model_temperature: 模型参数
            task
            file_and_method_json
        Returns:
            (responses,res): 生成的代码块

        """
        responses = self.query(prompt, num_responses, init_model_temperature)
        if task == "get_bug_position":
            return responses, [QueryEngine.extract_bug_loc(item) for item in responses]
        elif task == "get_bug_type":
            return responses, [QueryEngine.extract_bug_type(item, self.error_types) for item in responses]
        elif task == "get_bug_context":
            return responses, [QueryEngine.extract_bug_context(item, file_and_method_json) for item in responses]
        elif task == "get_bug_patch":
            return responses, [QueryEngine.extract_bug_patch(item, self.data_type) for item in responses]
        else:
            logger.info("The value of the task is incorrect!")
            raise ValueError("The value of the task is incorrect")

    @staticmethod
    def extract_bug_loc(response: str) -> List[str]:
        """
            从输入字符串中提取出所有可能表示代码行号或行号区间的文本片段。
            Args:
                response (str): 输入的字符串，通常包含对代码中 bug 位置的描述。
            Returns:
                List[str]: 提取出的所有符合格式的行号或行号区间字符串列表，
                           已去除前后空格，并过滤掉空字符串。
        """
        single_range_pattern = r'\d+(?:\s*-\s*\d+)?'
        candidates = re.findall(single_range_pattern, response)
        return [rng.strip() for rng in candidates if rng.strip()]


    @staticmethod
    def extract_bug_type(response: str, error_types: List[str]) -> List[str]:
        """
            从 response 中提取预定义的错误类型，并保持首次出现顺序且不重复。

            Args:
                response (str): 输入文本，通常包含 bug 类型描述。
                error_types (List[str]): 可识别的错误类型列表。

            Returns:
                List[str]: 按照首次出现顺序提取的 bug 类型列表，大小写敏感度取决于 ignore_case 参数。
        """
        found_errors = []
        pattern = r'\b(?:{})\b'.format('|'.join(re.escape(error) for error in error_types))
        regex = re.compile(pattern, re.IGNORECASE)

        # 遍历匹配结果，保留原始顺序，但不重复
        seen = set()
        for match in regex.findall(response):
            lower_match = match.lower()
            for error in error_types:
                if error.lower() == lower_match:
                    if error not in seen:
                        seen.add(error)
                        found_errors.append(error)
                    break
        return found_errors

    @staticmethod
    def extract_bug_context(response: str, file_and_method_json: Dict[str, Union[str, List[str]]]) -> List[str]:
        """
            从自然语言响应中提取出与 bug 相关的方法名上下文。
            Args:
                response (str): 包含 bug 描述的自然语言文本，可能提及某些方法名。
                file_and_method_json (Dict[str, Union[str, List[str]]]):包含代码文件及方法定义的 JSON 字典，
            Returns:
                List[str]: 在 `response` 中匹配到的方法名列表，
                            仅包含存在于 `file_and_method_json["Methods"]` 中的方法名，
                            大小写格式与原始定义一致，无重复，保持首次出现顺序。
        """
        methods = file_and_method_json.get("Methods", [])
        if isinstance(methods, str):
            methods = [methods]

        matched_methods = set()
        result_order = []

        for method in methods:
            # 提取方法名：如从 "def add_user(self):" 中提取 "add_user"
            match = re.search(r'\b([a-zA-Z_]\w*)\s*(?=$|\()', method)
            if not match:
                continue
            original_name = match.group(1)  # 原始大小写的方法名

            # 构建正则表达式，忽略大小写查找该方法名是否存在于 response 中
            pattern = re.compile(r'\b' + re.escape(original_name) + r'\b', re.IGNORECASE)
            if pattern.search(response):
                # 只添加第一次出现的，保留原始大小写格式和顺序
                if original_name not in matched_methods:
                    matched_methods.add(original_name)
                    result_order.append(original_name)
        return result_order

    @staticmethod
    def extract_bug_patch(response: str, data_type: str) -> str:
        """
            从 response 中提取代码补丁（code patch）。

            Args:
                response (str): 包含代码补丁的自然语言文本。
                data_type (str): 语言类型，支持 "java" 或 "py"

            Returns:
                str: 提取到的代码块内容；如果没有匹配项，根据 fallback_to_response 决定返回值
        """
        if data_type == "java":
            tagged_block = re.search(r"<code>(?P<code>[\s\S]*)</code>", response) # 函数查找第一个匹配 <code></code> 标签的代码块。
            if tagged_block: # tagged_block 代码块
                return tagged_block["code"]
            backticked_block = re.findall(r'```java\n([\s\S]*?)```', response)  # 匹配最后一个
            if backticked_block:
                return backticked_block[-1]  # 获取最后一个匹配项
            return response
        elif data_type == "py":
            tagged_block = re.search(r"<code>(?P<code>[\s\S]*)</code>", response) # 函数查找第一个匹配 <code></code> 标签的代码块。
            if tagged_block: # tagged_block 代码块
                return tagged_block["code"]
            backticked_block = re.findall(r'```python\n([\s\S]*?)```', response)  # 匹配最后一个
            if backticked_block:
                return backticked_block[-1]  # 获取最后一个匹配项
            return response
        else:
            raise TypeError(f"data_type only has two options: Java and Python, this is {data_type}")


    @staticmethod
    def messages(prompt: Union[str, Prompt]) -> List[Dict[str, str]]:
        """
        按固定格式记录日志消息
        Args:
            prompt: 提示(str或Prompt)

        Returns:
            messages: {"role": "user/assistant", "content": content}
        """
        if isinstance(prompt, str):
            messages = [
                {"role": "user", "content": prompt},
            ]
        else:
            messages = []
            for content in prompt.history:
                role, content = content
                if role == USER:
                    messages.append({"role": "user", "content": content})
                elif role == ASSISTANT:
                    messages.append({"role": "assistant", "content": content})
                else:
                    raise ValueError(f"Unidentified role: {role}")

            messages.append({"role": "user", "content": str(prompt)})
        return messages


class GPT3(QueryEngine):
    def __init__(self, data_type: str, error_types: List[str]) -> None:
        super().__init__(data_type, error_types)
        self.model = "gpt-3.5-turbo"
        self.url = ''
        self.headers = {"content-type": "application/json",
                        "Authorization": "Bearer sk-"}

    @override
    def raw_query(
        self,
        prompt: Union[str, Prompt],
        num_responses: int,
        init_model_temperature: float,
    ) -> List[str]:
        results = []
        try:
            for i in range(num_responses):
                model_temperature = init_model_temperature + (i * 0.4)
                data = {
                    "messages": self.messages(prompt),
                    "model": self.model,
                    "temperature": model_temperature,
                }
                data = json.dumps(data)
                logger.info(f"Response-{i+1}: Start obtain GPT-3 response, current temperature : {model_temperature}")
                response = requests.post(self.url, data=data, headers=self.headers, timeout=300)

                if response.status_code == 200:
                    settings.llm_call_count += 1
                    try:
                        # 解析JSON响应
                        response_json = response.json()
                        result = response_json["choices"][0]["message"]["content"]
                        results.append(result)
                        logger.info(f"Response-{i+1}: Successfully obtained GPT-3 response!")
                    except ValueError as e:
                        raise QueryError(e)
                else:
                    result = ""
                    results.append(result)
                    logger.error(f"Response-{i+1}: Failed to obtain GPT-3 response, trying again")
                time.sleep(3)
        except Exception as e:
            logger.info(f"Raise error,retry!")
            raise QueryError(e)
        return results


class GPT4(QueryEngine):
    def __init__(self, data_type: str, error_types: List[str]) -> None:
        super().__init__(data_type, error_types)
        self.model = "gpt-4-turbo"
        self.url = ''
        self.headers = {"content-type": "application/json",
                        "Authorization": "Bearer sk-"}


    @override
    def raw_query(
        self,
        prompt: Union[str, Prompt],
        num_responses: int,
        init_model_temperature: float,
    ) -> List[str]:
        results = []
        try:
            for i in range(num_responses):
                model_temperature = init_model_temperature + (i * 0.4)
                data = {
                    "messages": self.messages(prompt),
                    "model": self.model,
                    "temperature": model_temperature,
                }
                data = json.dumps(data)
                logger.info(f"Response-{i+1}: Start obtain GPT-4 response, current temperature : {model_temperature}")
                response = requests.post(self.url, data=data, headers=self.headers, timeout=300)

                if response.status_code == 200:
                    settings.llm_call_count += 1
                    try:
                        # 解析JSON响应
                        response_json = response.json()
                        result = response_json["choices"][0]["message"]["content"]
                        results.append(result)
                        logger.info(f"Response-{i+1}: Successfully obtained GPT-4 response")
                    except ValueError as e:
                        raise QueryError(e)
                else:
                    result = ""
                    results.append(result)
                    logger.error(f"Response-{i+1}: Failed to obtain GPT-4 response, trying again")
                time.sleep(3)
        except Exception as e:
            logger.info(f"Raise error,retry!")
            raise QueryError(e)
        return results



class QueryEngineFactory:
    @staticmethod
    def create_engine(model: str, data_type: str, error_types: List[str]) -> QueryEngine:
        match model:
            case "gpt-3":
                return GPT3(data_type, error_types)
            case "gpt-4":
                return GPT4(data_type, error_types)
            case _:
                raise ValueError(f"Unknown model: {model}")
