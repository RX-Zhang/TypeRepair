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
        instant = add(instant, valueToAdd);
        
        // Validate the resulting date to ensure it doesn't exceed the valid day range
        int year = iChronology.getYear(instant);
        int month = iChronology.getMonthOfYear(instant, year);
        int day = iChronology.getDayOfMonth(instant, year);
        int maxDay = iChronology.getDaysInYearMonth(year, month);
        
        if (day > maxDay) {
            // Adjust to the last valid day of the month
            day = maxDay;
        }
        
        // Return the adjusted values
        return iChronology.get(partial, iChronology.getYearMonthDayMillis(year, month, day));
    } else {
        return super.add(partial, fieldIndex, values, valueToAdd);
    }
}
