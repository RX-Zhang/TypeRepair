public List getValues(final Option option,
                      List defaultValues) {
    // grab the values given on the command line without defaults
    List valueList = getUndefaultedValues(option);

    // if empty and there are no explicit defaults, try to use the passed defaults
    if (valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // if still empty, try to get defaults from the object's defaultValues map
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
