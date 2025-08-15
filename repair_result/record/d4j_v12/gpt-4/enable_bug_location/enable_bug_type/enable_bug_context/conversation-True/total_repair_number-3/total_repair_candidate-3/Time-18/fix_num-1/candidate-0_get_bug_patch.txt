public long getDateTimeMillis(int year, int monthOfYear, int dayOfMonth,
                              int hourOfDay, int minuteOfHour,
                              int secondOfMinute, int millisOfSecond)
    throws IllegalArgumentException
{
    Chronology base;
    if ((base = getBase()) != null) {
        return base.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
    }

    // Assume date is Gregorian.
    long instant;
    try {
        instant = iGregorianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
    } catch (IllegalFieldValueException e) {
        // Possibly invalid date in Gregorian, check Julian.
        instant = iJulianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
        if (instant >= iCutoverMillis) {
            // Illegal date in cutover gap
            throw new IllegalArgumentException("Specified date does not exist");
        }
    }

    if (instant < iCutoverMillis) {
        long julianInstant = instant;
        instant = iJulianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
        if (instant >= iCutoverMillis) {
            // Illegal date in cutover gap
            throw new IllegalArgumentException("Specified date does not exist");
        }
        // Check if Julian date is valid and used properly
        if (instant != julianInstant) {
            instant = julianInstant;
        }
    }
    return instant;
}
