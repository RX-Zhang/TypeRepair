public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        // copy the defaultValues since we might augment it later
        if (defaultValues != null) {
            valueList = new ArrayList(defaultValues);
        } else {
            valueList = null;
        }
    }

    // augment the list with the class-level default values if needed
    if ((valueList == null) || valueList.isEmpty()) {
        List classDefaultValues = (List) this.defaultValues.get(option);
        if (classDefaultValues != null) {
            if (valueList == null) {
                valueList = new ArrayList(classDefaultValues);
            } else {
                valueList.addAll(classDefaultValues);
            }
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
