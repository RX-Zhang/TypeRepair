public List getValues(final Option option,
                      List defaultValues) {
    // Initialize the return list with values set on the command line
    List valueList = getUndefaultedValues(option);

    // If no command line values, use the passed defaultValues
    if (valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // If still empty, use the defaultValues stored in this object
    if (valueList == null || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
