public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if no command line values are present, use the provided default values
    if ((valueList == null) || valueList.isEmpty()) {
        if ((defaultValues == null) || defaultValues.isEmpty()) {
            valueList = (List) this.defaultValues.get(option);
            if (valueList == null) {
                valueList = Collections.EMPTY_LIST;
            }
        } else {
            valueList = defaultValues;
        }
    }

    return valueList;
}
