public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // If there are no values present, attempt to use the passed default values
    if ((valueList == null) || valueList.isEmpty()) {
        if (defaultValues != null && !defaultValues.isEmpty()) {
            // create a new list to avoid modifying the input defaultValues list
            valueList = new ArrayList(defaultValues);
        } else {
            // if passed default values are null or empty, fallback to stored defaultValues
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
