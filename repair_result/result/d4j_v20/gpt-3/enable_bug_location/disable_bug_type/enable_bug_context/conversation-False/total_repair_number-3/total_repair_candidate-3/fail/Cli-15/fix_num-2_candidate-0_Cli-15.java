public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        if (defaultValues != null && !defaultValues.isEmpty()) {
            valueList = defaultValues;
        } else {
            valueList = (List) this.defaultValues.get(option);
        }
    }

    // if there are more default values as specified, add them to the list.
    if (valueList == null) {
        return Collections.EMPTY_LIST;
    }

    // If valueList is from defaultValues map or defaultValues argument, 
    // we might have multiple defaults to merge. To ensure all defaults are present,
    // we create a copy and add missing defaults.
    List mergedValues = new ArrayList(valueList);

    // Add any missing default values from this.defaultValues if not already included
    List defVals = (List) this.defaultValues.get(option);
    if (defVals != null) {
        for (Object defVal : defVals) {
            if (!mergedValues.contains(defVal)) {
                mergedValues.add(defVal);
            }
        }
    }

    return mergedValues;
}
