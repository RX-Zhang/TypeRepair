public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if there are no values from the command line, create a new list and add default values individually
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList();
        if (defaultValues != null) {
            valueList.addAll(defaultValues);
        }
        List globalDefaults = (List) this.defaultValues.get(option);
        if (globalDefaults != null) {
            valueList.addAll(globalDefaults);
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
