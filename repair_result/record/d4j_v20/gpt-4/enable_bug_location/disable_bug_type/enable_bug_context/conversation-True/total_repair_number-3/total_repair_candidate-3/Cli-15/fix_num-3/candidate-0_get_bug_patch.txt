public List getValues(final Option option,
                      List defaultValues) {
    // First get the values explicitly set on the command line
    List valueList = getUndefaultedValues(option);

    // If no values were set on the command line, use the provided default values if any
    if (valueList.isEmpty() && defaultValues != null && !defaultValues.isEmpty()) {
        valueList = defaultValues;
    }

    // If still empty, use the option's predefined default values
    if (valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
        if (valueList == null) {
            valueList = Collections.EMPTY_LIST;
        }
    }

    return valueList;
}
