public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if there are no values or the list is empty, start with an empty list
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList();
    } else {
        // create a new list to avoid modifying the original list
        valueList = new ArrayList(valueList);
    }

    // if defaultValues is provided, add missing defaults to the list
    if (defaultValues != null && !defaultValues.isEmpty()) {
        valueList.addAll(defaultValues);
    }

    // if still empty or no defaultValues supplied, add defaultValues from the instance defaults
    if (valueList.isEmpty()) {
        List instanceDefaults = (List) this.defaultValues.get(option);
        if (instanceDefaults != null && !instanceDefaults.isEmpty()) {
            valueList.addAll(instanceDefaults);
        }
    }

    return valueList.isEmpty() ? Collections.EMPTY_LIST : valueList;
}
