public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList<>(defaultValues); // Create a new list with default values
    } else {
        valueList = new ArrayList<>(valueList); // Create a copy of existing values
    }

    // augment the list with the default values
    List optionDefaultValues = (List) this.defaultValues.get(option);
    if (optionDefaultValues != null && !optionDefaultValues.isEmpty()) {
        valueList.addAll(optionDefaultValues); // Add default values if they exist
    }

    return valueList.isEmpty() ? Collections.EMPTY_LIST : valueList;
}
