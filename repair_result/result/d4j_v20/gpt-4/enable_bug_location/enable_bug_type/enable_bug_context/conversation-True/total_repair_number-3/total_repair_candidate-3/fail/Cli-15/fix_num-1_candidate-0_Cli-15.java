public List getValues(final Option option,
                      List defaultValues) {
    // initialize the return list
    List valueList = (List) values.get(option);

    // if no values are set, start with defaultValues parameter if present
    if ((valueList == null) || valueList.isEmpty()) {
        if (defaultValues != null && !defaultValues.isEmpty()) {
            // Create a new list to avoid modifying the passed defaultValues list
            valueList = new ArrayList(defaultValues);
        } else {
            // then try to use this.defaultValues configured in the object
            List dv = (List) this.defaultValues.get(option);
            if (dv != null && !dv.isEmpty()) {
                valueList = new ArrayList(dv);
            }
        }
    }

    return valueList == null ? Collections.EMPTY_LIST : valueList;
}
