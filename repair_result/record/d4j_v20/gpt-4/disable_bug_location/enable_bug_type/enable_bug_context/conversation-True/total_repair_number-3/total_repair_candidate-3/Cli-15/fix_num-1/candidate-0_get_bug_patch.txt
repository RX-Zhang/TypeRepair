public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if no command line values were provided, check passed defaultValues
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // if still no values found, fallback to stored default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    // if valueList is still null, return empty list
    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
