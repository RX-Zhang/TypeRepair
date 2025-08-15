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
        int[] result = iChronology.get(partial, instant);

        // adjust day of month in case the original day was leap day Feb 29 and the
        // new month does not have 29 days
        int newMonth = result[0];
        int newDay = result[1];
        int maxDay = iChronology.getDaysInYearMonth(iChronology.getYear(instant), newMonth);
        if (newDay > maxDay) {
            newDay = maxDay;
            result[1] = newDay;
        }
        return result;
    } else {
        return super.add(partial, fieldIndex, values, valueToAdd);
    }
}
