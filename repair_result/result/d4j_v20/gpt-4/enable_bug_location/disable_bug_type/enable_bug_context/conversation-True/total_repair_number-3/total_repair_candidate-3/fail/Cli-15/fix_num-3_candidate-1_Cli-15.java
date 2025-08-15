public List getValues(final Option option,
                      List defaultValues) {
    // First grab the command line values, without defaults
    List valueList = getUndefaultedValues(option);

    // If no values were specified by the command line, use provided defaults
    if (valueList == null || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // If still no values, use the internally stored default values
    if (valueList == null || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
