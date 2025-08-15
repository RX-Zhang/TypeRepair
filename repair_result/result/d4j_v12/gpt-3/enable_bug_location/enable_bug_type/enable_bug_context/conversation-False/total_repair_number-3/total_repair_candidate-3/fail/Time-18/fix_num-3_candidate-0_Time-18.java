public long getDateTimeMillis(int year, int monthOfYear, int dayOfMonth,
                              int hourOfDay, int minuteOfHour,
                              int secondOfMinute, int millisOfSecond)
    throws IllegalArgumentException
{
    // Input validation for month and day
    if (monthOfYear < 1 || monthOfYear > 12) {
        throw new IllegalArgumentException("Month must be in the range [1,12]");
    }
    if (dayOfMonth < 1 || dayOfMonth > 31) {
        throw new IllegalArgumentException("Day must be in the range [1,31]");
    }
    
    // Handle month-specific day limits
    if (monthOfYear == 2) {
        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        if (dayOfMonth > (isLeapYear ? 29 : 28)) {
            throw new IllegalArgumentException("February " + year + " does not have " + dayOfMonth + " days");
        }
    } else if (monthOfYear == 4 || monthOfYear == 6 || monthOfYear == 9 || monthOfYear == 11) {
        if (dayOfMonth > 30) {
            throw new IllegalArgumentException("Month " + monthOfYear + " does not have " + dayOfMonth + " days");
        }
    }

    Chronology base;
    if ((base = getBase()) != null) {
        return base.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
    }

    // Assume date is Gregorian.
    long instant;
    instant = iGregorianChronology.getDateTimeMillis
        (year, monthOfYear, dayOfMonth,
         hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
    if (instant < iCutoverMillis) {
        // Maybe it's Julian.
        instant = iJulianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
        if (instant >= iCutoverMillis) {
            // Okay, it's in the illegal cutover gap.
            throw new IllegalArgumentException("Specified date does not exist");
        }
    }
    return instant;
}
