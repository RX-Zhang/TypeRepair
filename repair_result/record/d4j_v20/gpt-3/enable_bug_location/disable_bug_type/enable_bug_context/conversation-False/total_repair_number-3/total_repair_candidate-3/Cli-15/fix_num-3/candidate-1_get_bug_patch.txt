public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if the valueList is null or empty, try the provided defaultValues
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // if still null or empty, use the stored defaultValues
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    // if valueList is still null, return empty list
    if (valueList == null) {
        return Collections.EMPTY_LIST;
    }

    // create a new list to avoid modification of the original lists
    List<Object> combined = new ArrayList<>(valueList);

    // If there are defaults in this.defaultValues and they are not already included, add them
    List<Object> defVals = (List<Object>) this.defaultValues.get(option);
    if (defVals != null && defVals.size() > combined.size()) {
        // add missing defaults from defVals beyond the current size
        for (int i = combined.size(); i < defVals.size(); i++) {
            combined.add(defVals.get(i));
        }
    }

    return combined;
}
