public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if valueList is null or empty, start with a new list to collect values
    if (valueList == null || valueList.isEmpty()) {
        valueList = new ArrayList<>();
    } else {
        // make a copy to avoid modifying original stored list
        valueList = new ArrayList<>(valueList);
    }

    // add values from the provided defaultValues if not null
    if (defaultValues != null) {
        valueList.addAll(defaultValues);
    }

    // add values from the option's defaultValues if not null
    List defVals = (List) this.defaultValues.get(option);
    if (defVals != null) {
        valueList.addAll(defVals);
    }

    // if no values at all, return empty list
    return valueList.isEmpty() ? Collections.EMPTY_LIST : valueList;
}
