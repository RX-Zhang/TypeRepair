public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if (valueList == null || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // augment the list with the default values
    if (valueList.isEmpty()) {
        List additionalDefaults = (List) this.defaultValues.get(option);
        if (additionalDefaults != null) {
            valueList.addAll(additionalDefaults);
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
