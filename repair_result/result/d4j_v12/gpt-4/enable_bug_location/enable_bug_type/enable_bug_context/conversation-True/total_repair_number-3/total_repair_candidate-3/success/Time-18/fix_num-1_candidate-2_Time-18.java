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

    long instant;
    try {
        instant = iGregorianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
    } catch (IllegalFieldValueException e) {
        // If invalid in Gregorian, try Julian
        instant = iJulianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
    }

    if (instant < iCutoverMillis) {
        if (instant >= iCutoverMillis) {
            // Date falls in the cutover gap
            throw new IllegalArgumentException("Specified date does not exist");
        }
    }

    // Try Julian if instant is before cutover but invalid in Gregorian
    if (instant < iCutoverMillis) {
        try {
            instant = iJulianChronology.getDateTimeMillis
                (year, monthOfYear, dayOfMonth,
                 hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
        } catch (IllegalFieldValueException e) {
            throw e;
        }
        if (instant >= iCutoverMillis) {
            throw new IllegalArgumentException("Specified date does not exist");
        }
    }

    return instant;
}
