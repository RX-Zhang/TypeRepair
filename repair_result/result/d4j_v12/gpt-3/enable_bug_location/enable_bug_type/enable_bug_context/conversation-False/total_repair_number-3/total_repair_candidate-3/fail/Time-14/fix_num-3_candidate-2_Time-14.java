public int[] add(ReadablePartial partial, int fieldIndex, int[] values, int valueToAdd) {
    // overridden as superclass algorithm can't handle
    // 2004-02-29 + 48 months -> 2008-02-29 type dates
    if (valueToAdd == 0) {
        return values;
    }
    // month is largest field and being added to, such as month-day
    if (DateTimeUtils.isContiguous(partial)) {
        long instant = 0L;
        for (int i = 0, isize = partial.size(); i < isize; i++) {
            instant = partial.getFieldType(i).getField(iChronology).set(instant, values[i]);
        }
        // Check for proper handling of leap years and month adjustments
        int thisMonth = iChronology.getMonthOfYear(instant, iChronology.getYear(instant));
        if (thisMonth == 2 && values[1] == 29 && !iChronology.isLeapYear(iChronology.getYear(instant))) {
            values[1] = 28; // Adjust to the last valid day of February in a non-leap year
        }
        instant = add(instant, valueToAdd);
        return iChronology.get(partial, instant);
    } else {
        return super.add(partial, fieldIndex, values, valueToAdd);
    }
}
