from dataclasses import dataclass

# global variables
llm_call_count:int = 0
d4j_bin_path = "defects4j-2.1.0/framework/bin:"

@dataclass(frozen=True)
class Options:
    # JacksonXml-5  Jsoup-72  Closure-145
    dataset: str     # choices = ["d4j_v12", "d4j_v20", "QBs"]
    sample: str

    model: str = "gpt-4"   # choices = ["gpt-3", "gpt-4"]

    verbose: bool = False  # default = False

    total_repair_budget: int = 3  # default = 3
    task_candidate_num: int = 3   # default = 3
    repair_candidate_num: int = 3  # default = 3

    llm_retry_num: int = 3    # default = 3

    initial_temperature: float = 0.0  # default = 0.0

    # Ablation experiment [False, True]
    enable_bug_loc: bool = True  # default = True
    enable_bug_type: bool = True  # default = True
    enable_bug_context: bool = True  # default = True

    output_dir: str = "./../result"

    @property
    def conversation(self) -> bool:
        if self.model == "gpt-3":
            return False
        else:
            return True

    @property
    def rules_path(self) -> str:
        return f"rules/bug_rules.json"

    @property
    def dataset_type(self) -> str:
        if self.dataset == "d4j_v12" or self.dataset == "d4j_v20":
            return "java"
        elif self.dataset == "QBs":
            return "py"
        else:
            raise TypeError("dataset type error")

    @property
    def dataset_position(self) -> str:
        return f"data/reprocess_result/{self.dataset}"

    @property
    def d4j_single_func(self) -> str:
        return "data/Defects4j/single_function_repair.json"

    @property
    def qbs_test_path(self) -> str:
        return "data/QuixBugs_test"

    @property
    def work_dir(self) -> str:
        return (f"workspace/{self.dataset}/{self.model}/"
                f"{self.bug_location_state}/{self.bug_type_state}/{self.bug_context_state}/conversation-{self.conversation}/"
                f"total_repair_number-{self.total_repair_budget}/total_repair_candidate-{self.repair_candidate_num}/"
                f"{self.sample}")

    @property
    def work_res_dir(self) -> str:
        return f"{self.work_dir}/results"

    @property
    def res_record_dir(self) -> str:
        return (f"repair_result/record/{self.dataset}/{self.model}/"
                f"{self.bug_location_state}/{self.bug_type_state}/{self.bug_context_state}/conversation-{self.conversation}/"
                f"total_repair_number-{self.total_repair_budget}/total_repair_candidate-{self.repair_candidate_num}/"
                f"{self.sample}")


    @property
    def res_result_dir(self) -> str:
        return (f"repair_result/result/{self.dataset}/{self.model}/"
                f"{self.bug_location_state}/{self.bug_type_state}/{self.bug_context_state}/conversation-{self.conversation}/"
                f"total_repair_number-{self.total_repair_budget}/total_repair_candidate-{self.repair_candidate_num}")

    @property
    def max_tokens(self) -> int:
        if self.model == "gpt-3":
            return 16385
        else:
            return 128000

    @property
    def log_dir(self) -> str:
        return f"{self.work_dir}/log"

    @property
    def bug_location_state(self) -> str:
        return "enable_bug_location" if self.enable_bug_loc else "disable_bug_location"

    @property
    def bug_type_state(self) -> str:
        return "enable_bug_type" if self.enable_bug_type else "disable_bug_type"

    @property
    def bug_context_state(self) -> str:
        return "enable_bug_context" if self.enable_bug_context else "disable_bug_context"