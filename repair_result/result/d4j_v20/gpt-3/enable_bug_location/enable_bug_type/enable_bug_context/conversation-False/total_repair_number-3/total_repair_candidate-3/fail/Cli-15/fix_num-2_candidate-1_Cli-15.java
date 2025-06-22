public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList<>(defaultValues);
    } else {
        // augment the list with the default values if they are not already present
        List defaultValueList = (List) this.defaultValues.get(option);
        if (defaultValueList != null && !defaultValueList.isEmpty()) {
            valueList.addAll(defaultValueList);
        }
    }

    return valueList.isEmpty() ? Collections.EMPTY_LIST : valueList;
}
