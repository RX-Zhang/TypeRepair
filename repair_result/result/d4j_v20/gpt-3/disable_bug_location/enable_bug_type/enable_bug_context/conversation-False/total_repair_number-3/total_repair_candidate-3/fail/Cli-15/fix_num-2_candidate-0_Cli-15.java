public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    if ((valueList == null) || valueList.isEmpty()) {
        // start with an empty list to combine defaults
        valueList = new ArrayList<>();
        // add defaults from parameter defaultValues first
        if (defaultValues != null && !defaultValues.isEmpty()) {
            valueList.addAll(defaultValues);
        }
        // then add any defaults from the instance defaultValues map
        List instanceDefaults = (List) this.defaultValues.get(option);
        if (instanceDefaults != null && !instanceDefaults.isEmpty()) {
            valueList.addAll(instanceDefaults);
        }
    }
    
    return valueList == null || valueList.isEmpty() ? Collections.EMPTY_LIST : valueList;
}
