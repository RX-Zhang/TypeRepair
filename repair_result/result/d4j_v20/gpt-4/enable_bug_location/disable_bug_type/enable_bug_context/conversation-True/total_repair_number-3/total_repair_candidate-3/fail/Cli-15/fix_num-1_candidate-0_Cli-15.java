public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if no values are set, start with an empty list
    if (valueList == null) {
        valueList = new java.util.ArrayList();
    } else {
        // create a copy of the list to avoid modifying the internal state
        valueList = new java.util.ArrayList(valueList);
    }

    // add the default values passed as argument if not present
    if (defaultValues != null && !defaultValues.isEmpty()) {
        valueList.addAll(defaultValues);
    }

    // add any default values stored in the instance (this.defaultValues)
    List instanceDefaults = (List) this.defaultValues.get(option);
    if (instanceDefaults != null && !instanceDefaults.isEmpty()) {
        valueList.addAll(instanceDefaults);
    }

    return valueList;
}
