public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values if no values found in values map
    if (valueList == null || valueList.isEmpty()) {
        if (defaultValues != null && !defaultValues.isEmpty()) {
            valueList = defaultValues;
        } else {
            valueList = (List) this.defaultValues.get(option);
            if (valueList == null) {
                valueList = Collections.EMPTY_LIST;
            }
        }
    }

    return valueList;
}
