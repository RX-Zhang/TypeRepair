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

    // if there are more default values as specified, add them to the list.
    if (valueList != null && defaultValues != null && valueList != defaultValues) {
        // create a new list copy to avoid modifying original lists
        List combined = new ArrayList(valueList);
        for (Object defVal : defaultValues) {
            if (!combined.contains(defVal)) {
                combined.add(defVal);
            }
        }
        valueList = combined;
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
