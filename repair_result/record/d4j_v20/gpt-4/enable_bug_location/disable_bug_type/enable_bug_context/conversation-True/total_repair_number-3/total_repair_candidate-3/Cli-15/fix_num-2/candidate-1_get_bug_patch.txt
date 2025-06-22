public List getValues(final Option option,
                      List defaultValues) {
    // First grab the command line values without defaults
    List valueList = getUndefaultedValues(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // augment the list with the default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
