public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    if ((valueList == null) || valueList.isEmpty()) {
        // start with empty list
        valueList = new ArrayList();

        // add provided defaultValues first, if any
        if (defaultValues != null) {
            valueList.addAll(defaultValues);
        }

        // then add class-level defaultValues, if any
        List defVals = (List) this.defaultValues.get(option);
        if (defVals != null && !defVals.isEmpty()) {
            valueList.addAll(defVals);
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
