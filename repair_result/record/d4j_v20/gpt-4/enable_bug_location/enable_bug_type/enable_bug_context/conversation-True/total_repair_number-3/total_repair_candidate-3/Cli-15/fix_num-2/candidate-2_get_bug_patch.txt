public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // if still empty, get class-level default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    // If valueList is not null but smaller than maximum expected,
    // augment with default values to meet maximum number of arguments
    if (valueList != null && !valueList.isEmpty()) {
        List classDefaults = (List) this.defaultValues.get(option);
        if (classDefaults != null && !classDefaults.isEmpty()) {
            int totalValues = valueList.size();
            int max = Integer.MAX_VALUE;
            if (option != null && option.getArguments() != null && !option.getArguments().isEmpty()) {
                Argument arg = (Argument) option.getArguments().get(0);
                max = arg.getMaximum();
            }
            if (max != Integer.MAX_VALUE) {
                List combined = new ArrayList(valueList);
                int defaultsIndex = 0;
                while (combined.size() < max && defaultsIndex < classDefaults.size()) {
                    combined.add(classDefaults.get(defaultsIndex));
                    defaultsIndex++;
                }
                valueList = combined;
            }
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
