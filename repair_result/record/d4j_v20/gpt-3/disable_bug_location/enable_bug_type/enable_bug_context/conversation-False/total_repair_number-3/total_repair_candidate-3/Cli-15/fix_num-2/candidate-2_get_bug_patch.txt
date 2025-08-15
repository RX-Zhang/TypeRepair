public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if no values provided, use the supplied defaultValues
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // if still no values, use the class default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    // if valueList is still null, return empty list to avoid null pointer
    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
