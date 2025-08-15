public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // If no values specified on command line, start with a new list for defaults
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList();

        // Add default values passed as argument
        if (defaultValues != null) {
            valueList.addAll(defaultValues);
        }

        // Add default values from the instance defaultValues map
        List instanceDefaults = (List) this.defaultValues.get(option);
        if (instanceDefaults != null) {
            valueList.addAll(instanceDefaults);
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
