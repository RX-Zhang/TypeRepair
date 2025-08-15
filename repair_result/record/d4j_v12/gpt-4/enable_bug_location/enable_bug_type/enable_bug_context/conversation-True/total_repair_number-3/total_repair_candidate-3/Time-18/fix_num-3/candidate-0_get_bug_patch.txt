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

    // Attempt to create the datetime assuming Gregorian chronology
    long instant = iGregorianChronology.getDateTimeMillis
        (year, monthOfYear, dayOfMonth,
         hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);

    if (instant < iCutoverMillis) {
        // If before cutover, verify if date is valid in Julian chronology
        long julianInstant = iJulianChronology.getDateTimeMillis
            (year, monthOfYear, dayOfMonth,
             hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond);
        if (julianInstant < iCutoverMillis) {
            // Valid Julian date before cutover
            instant = julianInstant;
        } else {
            // Date falls into the cutover gap - not valid
            throw new IllegalArgumentException("Specified date does not exist");
        }
    }
    return instant;
}
