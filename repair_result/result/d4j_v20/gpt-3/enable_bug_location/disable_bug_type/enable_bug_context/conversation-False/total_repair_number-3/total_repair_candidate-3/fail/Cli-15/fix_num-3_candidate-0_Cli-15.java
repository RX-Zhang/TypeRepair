public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // if still null or empty, grab from this.defaultValues
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    // if there are more default values as specified, add them to the list.
    if (valueList != null && defaultValues != null && valueList != defaultValues) {
        // Create a new list to avoid modifying the original lists
        List combined = new ArrayList(valueList.size() + defaultValues.size());
        combined.addAll(valueList);
        for (Object defVal : defaultValues) {
            if (!combined.contains(defVal)) {
                combined.add(defVal);
            }
        }
        valueList = combined;
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
