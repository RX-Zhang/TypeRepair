public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // if valueList is still null or empty, try the defaultValues stored in the class
    if ((valueList == null) || valueList.isEmpty()) {
        List classDefaults = (List) this.defaultValues.get(option);
        if (classDefaults != null) {
            valueList = new ArrayList(classDefaults);
        }
    }

    // If both valueList and defaultValues are non-empty and we want to augment the values
    if (valueList != null && !valueList.isEmpty()) {
        // if defaultValues is non-null and different from valueList, add all missing defaults
        if (defaultValues != null && defaultValues != valueList) {
            for (Object defVal : defaultValues) {
                if (!valueList.contains(defVal)) {
                    valueList.add(defVal);
                }
            }
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
