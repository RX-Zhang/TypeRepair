public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // augment the list with the default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    // if there are default values specified in this.defaultValues, and valueList
    // already contains some values (but possibly fewer than the max),
    // we should augment valueList to include them up to the expected number.
    // Copy valueList to avoid modifying internal state directly
    if (valueList != null && !valueList.isEmpty()) {
        List defaults = (List) this.defaultValues.get(option);
        if (defaults != null && !defaults.isEmpty()) {
            // Create a new list to avoid mutating internal lists
            List combined = new java.util.ArrayList(valueList);
            int maxSize = combined.size();
            // If an option has a maximum number of arguments, 
            // try to fill up to that from defaults (this requires external knowledge, 
            // so for safety, just add any defaults not present)
            for (Object defVal : defaults) {
                if (!combined.contains(defVal)) {
                    combined.add(defVal);
                }
            }
            valueList = combined;
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
