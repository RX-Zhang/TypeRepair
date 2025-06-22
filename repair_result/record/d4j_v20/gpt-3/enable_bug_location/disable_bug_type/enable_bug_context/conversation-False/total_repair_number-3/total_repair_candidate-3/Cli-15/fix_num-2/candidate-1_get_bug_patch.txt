public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // if still no values, try option's defaultValues
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    // if valueList has fewer elements than required (minimum), 
    // try to add default values if any are missing
    if (valueList != null && !(valueList instanceof ArrayList)) {
        valueList = new ArrayList(valueList);
    }

    if (valueList == null) {
        valueList = Collections.EMPTY_LIST;
    }
    
    return valueList;
}
