public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // grab the correct default values
    if ((valueList == null) || valueList.isEmpty()) {
        valueList = defaultValues;
    }

    // augment the list with the default values
    if ((valueList == null) || valueList.isEmpty()) {
        List defaults = (List) this.defaultValues.get(option);
        if (defaults != null) {
            valueList = new ArrayList(defaults);
        }
    }
    
    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
