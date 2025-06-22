public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if we have values from 'values', create a new list and add them first
    if (valueList != null && !valueList.isEmpty()) {
        // Make a copy to avoid modifying original list
        valueList = new ArrayList(valueList);
    } else {
        // else start with defaultValues (method argument) if provided
        if (defaultValues != null && !defaultValues.isEmpty()) {
            valueList = new ArrayList(defaultValues);
        } else {
            // else start with defaultValues set in the object
            List defVals = (List) this.defaultValues.get(option);
            if (defVals != null && !defVals.isEmpty()) {
                valueList = new ArrayList(defVals);
            } else {
                valueList = new ArrayList();
            }
        }
    }

    // if the values from 'values' exist and defaultValues or object defaultValues exist,
    // add missing default values at the end if not already present.

    if (values.containsKey(option)) {
        // We have values already - see if defaults need to be appended

        List toAppend = null;
        if (defaultValues != null && !defaultValues.isEmpty()) {
            toAppend = defaultValues;
        } else {
            toAppend = (List) this.defaultValues.get(option);
        }

        if (toAppend != null && !toAppend.isEmpty()) {
            // add those defaults that are not already in valueList
            for (Object defaultValue : toAppend) {
                if (!valueList.contains(defaultValue)) {
                    valueList.add(defaultValue);
                }
            }
        }
    }

    return valueList == null || valueList.isEmpty() ? Collections.EMPTY_LIST : valueList;
}
