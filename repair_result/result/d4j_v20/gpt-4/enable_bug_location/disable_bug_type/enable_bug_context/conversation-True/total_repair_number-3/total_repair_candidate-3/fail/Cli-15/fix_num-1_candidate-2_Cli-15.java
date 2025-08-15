public List getValues(final Option option,
                      List defaultValues) {
    // Get the user-supplied values without default augmentation
    List valueList = getUndefaultedValues(option);

    // If no user values, use the defaultValues parameter first
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // If still null or empty, fallback to stored default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = (List) this.defaultValues.get(option);
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
