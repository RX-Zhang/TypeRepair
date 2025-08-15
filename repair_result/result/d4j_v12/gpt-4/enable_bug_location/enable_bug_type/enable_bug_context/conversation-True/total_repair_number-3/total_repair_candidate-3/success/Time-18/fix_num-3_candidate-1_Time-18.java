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
        // If the dayOfMonth is invalid for Gregorian but possibly valid for Julian,
        // attempt Julian calculation for dates before cutover.
        instant = iJulianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
        if (instant >= iCutoverMillis) {
            throw new IllegalArgumentException("Specified date does not exist");
        }
        return instant;
    }

    if (instant < iCutoverMillis) {
        // Maybe it's Julian.
        long julianInstant = iJulianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
        if (julianInstant >= iCutoverMillis) {
            // Okay, it's in the illegal cutover gap.
            throw new IllegalArgumentException("Specified date does not exist");
        }
        // Use Julian instant if earlier than Gregorian instant (before cutover).
        instant = julianInstant;
    }

    return instant;
}
