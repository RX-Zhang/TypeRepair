public long getDateTimeMillis(int year, int monthOfYear, int dayOfMonth,
                              int hourOfDay, int minuteOfHour,
                              int secondOfMinute, int millisOfSecond)
    throws IllegalArgumentException
{
    // Input validation
    if (monthOfYear < 1 || monthOfYear > 12) {
        throw new IllegalArgumentException("Month must be in the range [1, 12]");
    }
    if (dayOfMonth < 1 || dayOfMonth > 31) {
        throw new IllegalArgumentException("Day must be in the range [1, 31]");
    }
    if (hourOfDay < 0 || hourOfDay > 23) {
        throw new IllegalArgumentException("Hour must be in the range [0, 23]");
    }
    if (minuteOfHour < 0 || minuteOfHour > 59) {
        throw new IllegalArgumentException("Minute must be in the range [0, 59]");
    }
    if (secondOfMinute < 0 || secondOfMinute > 59) {
        throw new IllegalArgumentException("Second must be in the range [0, 59]");
    }
    if (millisOfSecond < 0 || millisOfSecond > 999) {
        throw new IllegalArgumentException("Millis must be in the range [0, 999]");
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
