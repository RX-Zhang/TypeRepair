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
        
        // Check for leap year adjustment
        int thisYear = iChronology.getYear(instant);
        int thisMonth = iChronology.getMonthOfYear(instant, thisYear);
        
        // Calculate new instant after adding months
        instant = add(instant, valueToAdd);
        
        // Updated values based on new instant
        int[] newValues = iChronology.get(partial, instant);

        // Validate and adjust for leap year to ensure day of month is valid
        if (thisMonth == 2 && values[1] == 29) { // February and leap year condition
            if (!iChronology.isLeapYear(thisYear + (valueToAdd / 12))) {
                newValues[1] = Math.min(newValues[1], 28); // Adjust to 28 if not a leap year
            }
        }

        return newValues;
    } else {
        return super.add(partial, fieldIndex, values, valueToAdd);
    }
}
