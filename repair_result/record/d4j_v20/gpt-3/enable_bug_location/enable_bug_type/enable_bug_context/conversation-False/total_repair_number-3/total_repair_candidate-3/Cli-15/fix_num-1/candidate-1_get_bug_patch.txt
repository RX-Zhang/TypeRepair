public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if (valueList == null || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // augment the list with the default values
    if (valueList == null || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    // Ensure we return a list that contains all values
    if (valueList == null) {
        return Collections.emptyList();
    }

    // If default values exist, add them to the valueList
    List defaultList = (List) this.defaultValues.get(option);
    if (defaultList != null && !defaultList.isEmpty()) {
        valueList.addAll(defaultList);
    }

    return valueList;
}
