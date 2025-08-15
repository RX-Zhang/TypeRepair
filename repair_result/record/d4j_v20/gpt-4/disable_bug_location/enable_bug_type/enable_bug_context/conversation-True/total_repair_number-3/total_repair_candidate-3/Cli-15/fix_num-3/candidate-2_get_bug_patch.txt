public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values if valueList is null or empty
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = new ArrayList<>();
        if (defaultValues != null) {
            valueList.addAll(defaultValues);
        }
        List internalDefaults = (List) this.defaultValues.get(option);
        if (internalDefaults != null) {
            valueList.addAll(internalDefaults);
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
