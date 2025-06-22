public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    if ((valueList == null) || valueList.isEmpty()) {
        // start with a new list to combine values and defaults
        valueList = new ArrayList();

        // add values from provided defaultValues first, if any
        if (defaultValues != null && !defaultValues.isEmpty()) {
            valueList.addAll(defaultValues);
        }

        // add values from instance defaultValues map, if any
        List instanceDefaults = (List) this.defaultValues.get(option);
        if (instanceDefaults != null && !instanceDefaults.isEmpty()) {
            valueList.addAll(instanceDefaults);
        }

        if (valueList.isEmpty()) {
            valueList = null; // keep consistent with original behavior
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
