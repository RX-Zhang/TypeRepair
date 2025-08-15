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
        // If date is invalid in Gregorian due to leap day, try Julian
        instant = iJulianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
        if (instant >= iCutoverMillis) {
            // It's in the illegal cutover gap.
            throw new IllegalArgumentException("Specified date does not exist");
        }
        return instant;
    }

    if (instant < iCutoverMillis) {
        // Maybe it's Julian.
        long julianInstant;
        try {
            julianInstant = iJulianChronology.getDateTimeMillis
                (year, monthOfYear, dayOfMonth,
                 hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
        } catch (IllegalFieldValueException e) {
            // Julian invalid too, rethrow original exception
            throw e;
        }
        if (julianInstant >= iCutoverMillis) {
            // It's illegal cutover gap.
            throw new IllegalArgumentException("Specified date does not exist");
        }
    }
    return instant;
}
