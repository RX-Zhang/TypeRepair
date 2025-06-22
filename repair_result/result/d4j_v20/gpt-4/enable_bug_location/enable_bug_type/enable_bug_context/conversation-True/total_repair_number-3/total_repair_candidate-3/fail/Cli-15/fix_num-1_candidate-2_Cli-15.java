public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = getUndefaultedValues(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        if (defaultValues != null && !defaultValues.isEmpty()) {
            // copy the list first to avoid modifying the original defaults
            valueList = new ArrayList(defaultValues);
        } else {
            valueList = (List) this.defaultValues.get(option);
            if (valueList != null) {
                valueList = new ArrayList(valueList);
            }
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
