public long getDateTimeMillis(int year, int monthOfYear, int dayOfMonth,
                              int hourOfDay, int minuteOfHour,
                              int secondOfMinute, int millisOfSecond)
    throws IllegalArgumentException
{
    // Input validation for year, month, day, hour, minute, second, and millis
    if (year < 0 || monthOfYear < 1 || monthOfYear > 12 || dayOfMonth < 1 || 
        hourOfDay < 0 || hourOfDay > 23 || minuteOfHour < 0 || minuteOfHour > 59 || 
        secondOfMinute < 0 || secondOfMinute > 59 || millisOfSecond < 0 || millisOfSecond > 999) {
        throw new IllegalArgumentException("Invalid date or time values provided");
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
