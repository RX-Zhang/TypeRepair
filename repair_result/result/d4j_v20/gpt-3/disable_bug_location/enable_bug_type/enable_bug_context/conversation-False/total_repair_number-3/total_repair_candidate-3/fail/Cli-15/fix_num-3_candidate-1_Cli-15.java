public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        // create a new list to avoid modifying input lists directly
        if (defaultValues != null && !defaultValues.isEmpty()) {
            valueList = new ArrayList(defaultValues);
        } else {
            List defVals = (List) this.defaultValues.get(option);
            if (defVals != null && !defVals.isEmpty()) {
                valueList = new ArrayList(defVals);
            } else {
                valueList = Collections.EMPTY_LIST;
            }
        }
    } else {
        // If valueList exists, we also need to augment it with any default values specified for this option 
        List defVals = (List) this.defaultValues.get(option);
        if (defVals != null && !defVals.isEmpty()) {
            // Create a new list copying current values to avoid modifying internal map
            List augmentedList = new ArrayList(valueList);
            // Add default values only if they are not already present
            for (Object defVal : defVals) {
                if (!augmentedList.contains(defVal)) {
                    augmentedList.add(defVal);
                }
            }
            valueList = augmentedList;
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
