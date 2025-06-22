"""
主程序入口模块，负责启动整个代码修复流程。
"""
import json
import logging
import os
import shutil
import settings

from argparse_dataclass import ArgumentParser
from llms import QueryEngineFactory
from bug_repair import Repair
from validate import Validate

from utils import setup_logger, get_rules, get_file_and_method_json, get_bug_dict

logger = logging.getLogger("CodeRepair")

def main(options):
    # clear work_dir
    if os.path.exists(options.work_dir):
        shutil.rmtree(options.work_dir)
    os.makedirs(options.work_dir)

    # set log
    setup_logger(options.log_dir, options.verbose)

    # rules and error_types
    rules = get_rules(options)
    error_types = list(rules.keys())

    # file_and_method_json
    file_and_method_json = get_file_and_method_json(options)

    # Create a translation engine
    query_engine = QueryEngineFactory.create_engine(options.model, options.dataset_type, error_types)

    # Create a repair tool
    if options.dataset_type == "java":
        repair_tool = Repair(options, query_engine, error_types, rules, file_and_method_json)
    else:
        repair_tool = Repair(options, query_engine, error_types, rules)

    # Create a validate tool
    bug_dict = get_bug_dict(options)
    validate_tool = Validate(options, bug_dict)

    logger.info(f"============== Start fixing code: {options.dataset}/{options.sample} ==============")
    for fix_idx in range(1, options.total_repair_budget+1):
        # Get bug location
        logger.info(f"The {fix_idx}-th fix:")
        if options.enable_bug_loc:
            bug_position, histories = repair_tool.get_bug_position(fix_idx)
        else:
            bug_position = ""
            histories = []

        # Get bug type
        if options.enable_bug_type:
            bug_type, new_histories = repair_tool.get_bug_type(fix_idx, bug_position, histories)
        else:
            bug_type = ""
            new_histories = histories

        # Get bug context
        if options.enable_bug_context and options.dataset_type == "java" and file_and_method_json["ClassName"] != "":
            bug_context, last_histories = repair_tool.get_bug_context(fix_idx, new_histories)
        else:
            bug_context = ""
            last_histories = new_histories

        # Get patch
        repair_result = repair_tool.get_bug_patch(fix_idx, bug_position, bug_type, bug_context, last_histories)

        if not repair_result:
            logger.info(f"The patch generated for the {fix_idx}-th time is empty. retry repair!")
            continue

        # validate patch and save result
        validate_tool.validate_patch(repair_result, fix_idx)


if __name__ == "__main__":
    parser = ArgumentParser(settings.Options)
    setting_option = parser.parse_args()
    main(setting_option)
    logger.info(f"Program runs successfully, called LLM {settings.llm_call_count} times successfully")
    print(f"Program runs successfully, called LLM {settings.llm_call_count} times successfully")