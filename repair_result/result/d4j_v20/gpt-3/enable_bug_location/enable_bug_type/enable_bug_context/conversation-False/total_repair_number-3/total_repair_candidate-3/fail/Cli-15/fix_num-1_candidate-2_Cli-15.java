public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList<>(defaultValues);
    }

    // augment the list with the default values
    if (valueList.isEmpty()) {
        List optionDefaults = (List) this.defaultValues.get(option);
        if (optionDefaults != null) {
            valueList.addAll(optionDefaults);
        }
    }

    return valueList.isEmpty() ? Collections.EMPTY_LIST : valueList;
}
