public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    if (valueList == null) {
        // create a new list to hold values including defaults
        valueList = new ArrayList();
        values.put(option, valueList);
    }

    // add the values passed as defaultValues if the list is currently empty
    if (valueList.isEmpty() && defaultValues != null) {
        valueList.addAll(defaultValues);
    }

    // add the defaultValues set in the object if still empty
    if (valueList.isEmpty()) {
        List defaults = (List) this.defaultValues.get(option);
        if (defaults != null) {
            valueList.addAll(defaults);
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
