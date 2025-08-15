public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        if (defaultValues != null && !defaultValues.isEmpty()) {
            valueList = defaultValues;
        } else {
            valueList = (List) this.defaultValues.get(option);
        }
    }

    // if valueList is still null, return empty list
    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
