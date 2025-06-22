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
    if (valueList != null) {
        List internalDefaults = (List) this.defaultValues.get(option);
        if (internalDefaults != null && !internalDefaults.isEmpty()) {
            if (valueList != internalDefaults) {
                List mergedList = new java.util.ArrayList(valueList);
                for (Object def : internalDefaults) {
                    if (!mergedList.contains(def)) {
                        mergedList.add(def);
                    }
                }
                valueList = mergedList;
            }
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
