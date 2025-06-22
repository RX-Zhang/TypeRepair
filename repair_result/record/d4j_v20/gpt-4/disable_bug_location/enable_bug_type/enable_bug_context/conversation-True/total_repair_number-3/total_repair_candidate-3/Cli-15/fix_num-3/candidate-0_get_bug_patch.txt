public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    if (valueList == null) {
        // if no values have been set, start a new list 
        valueList = new ArrayList<>();
    } else {
        // create a copy so the original list isn't modified
        valueList = new ArrayList<>(valueList);
    }

    // add the provided default values if not present
    if (defaultValues != null) {
        for (Object defVal : defaultValues) {
            if (!valueList.contains(defVal)) {
                valueList.add(defVal);
            }
        }
    }

    // add the class-level defaultValues for the option if not present
    List classDefaults = (List) this.defaultValues.get(option);
    if (classDefaults != null) {
        for (Object defVal : classDefaults) {
            if (!valueList.contains(defVal)) {
                valueList.add(defVal);
            }
        }
    }

    return valueList.isEmpty() ? Collections.EMPTY_LIST : valueList;
}
