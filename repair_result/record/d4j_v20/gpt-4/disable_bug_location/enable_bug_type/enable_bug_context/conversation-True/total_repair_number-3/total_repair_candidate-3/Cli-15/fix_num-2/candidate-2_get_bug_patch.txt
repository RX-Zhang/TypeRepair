public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if there are no values, start with a new list
    if (valueList == null) {
        valueList = new ArrayList();
    }

    // add all explicitly set values
    if (valueList.isEmpty() && defaultValues != null && !defaultValues.isEmpty()) {
        valueList.addAll(defaultValues);
    }

    // add defaultValues from this.defaultValues if needed and not already added
    if (valueList.isEmpty()) {
        List defVals = (List) this.defaultValues.get(option);
        if (defVals != null && !defVals.isEmpty()) {
            valueList.addAll(defVals);
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
