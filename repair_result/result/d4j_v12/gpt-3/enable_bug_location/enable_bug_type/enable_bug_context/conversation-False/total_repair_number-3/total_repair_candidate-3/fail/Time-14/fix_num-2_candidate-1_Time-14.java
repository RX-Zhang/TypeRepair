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
        // Validate the instant before adding months to prevent IllegalFieldValueException
        int thisYear = iChronology.getYear(instant);
        int thisMonth = iChronology.getMonthOfYear(instant, thisYear);
        int thisDom = iChronology.getDayOfMonth(instant, thisYear);
        
        // Calculate the new instant after adding months
        long newInstant = add(instant, valueToAdd);
        
        // Adjust the day of month if necessary
        int newMonth = iChronology.getMonthOfYear(newInstant, thisYear);
        int maxDom = iChronology.getDaysInYearMonth(thisYear, newMonth);
        if (thisDom > maxDom) {
            thisDom = maxDom; // Adjust to the maximum day of the new month
        }
        
        newInstant = iChronology.getYearMonthDayMillis(thisYear, newMonth, thisDom);
        return iChronology.get(partial, newInstant);
    } else {
        return super.add(partial, fieldIndex, values, valueToAdd);
    }
}
