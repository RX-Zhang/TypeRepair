public List getValues(final Option option,
                      List defaultValues) {
    // get values without defaults applied
    List valueList = getUndefaultedValues(option);

    // if no actual values, try the provided defaultValues list
    if (valueList == null || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // if still no values, try the default values stored in this.defaultValues
    if (valueList == null || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
