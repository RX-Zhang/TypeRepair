public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList<>(defaultValues); // Create a new list to avoid modifying the original
    }

    // augment the list with the default values
    if (valueList.isEmpty()) {
        List defaultValueList = (List) this.defaultValues.get(option);
        if (defaultValueList != null) {
            valueList.addAll(defaultValueList); // Add all default values if they exist
        }
    }

    return valueList.isEmpty() ? Collections.EMPTY_LIST : valueList;
}
