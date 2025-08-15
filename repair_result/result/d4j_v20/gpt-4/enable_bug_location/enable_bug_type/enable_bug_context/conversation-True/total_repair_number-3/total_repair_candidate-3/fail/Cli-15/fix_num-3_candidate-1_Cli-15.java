public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if no values found, then start a new list to add defaults if any
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList();
        if (defaultValues != null) {
            valueList.addAll(defaultValues);
        }
        List classDefaultValues = (List) this.defaultValues.get(option);
        if (classDefaultValues != null) {
            valueList.addAll(classDefaultValues);
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
