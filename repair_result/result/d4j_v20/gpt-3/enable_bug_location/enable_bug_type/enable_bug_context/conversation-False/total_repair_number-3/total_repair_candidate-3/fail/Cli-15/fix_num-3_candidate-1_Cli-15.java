public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList<>(defaultValues);
    } else {
        // augment the list with the default values if needed
        List defaultList = (List) this.defaultValues.get(option);
        if (defaultList != null && !defaultList.isEmpty()) {
            valueList.addAll(defaultList);
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
