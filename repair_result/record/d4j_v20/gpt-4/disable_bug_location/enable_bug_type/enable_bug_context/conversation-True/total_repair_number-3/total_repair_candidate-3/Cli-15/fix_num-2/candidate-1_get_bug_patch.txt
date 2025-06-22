public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values if no values were set
    if (valueList == null || valueList.isEmpty()) {
        // start with passed-in default values
        if (defaultValues != null && !defaultValues.isEmpty()) {
            // make a new list to avoid modifying the passed in defaultValues
            valueList = new ArrayList(defaultValues);
        } else {
            // fallback to the default values stored in this.defaultValues
            List storedDefaults = (List) this.defaultValues.get(option);
            if (storedDefaults != null && !storedDefaults.isEmpty()) {
                valueList = new ArrayList(storedDefaults);
            } else {
                valueList = Collections.EMPTY_LIST;
            }
        }
    }

    return valueList;
}
