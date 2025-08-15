public static DateTimeZone forOffsetHoursMinutes(int hoursOffset, int minutesOffset) throws IllegalArgumentException {
    if (hoursOffset == 0 && minutesOffset == 0) {
        return DateTimeZone.UTC;
    }
    if (hoursOffset < -23 || hoursOffset > 23) {
        throw new IllegalArgumentException("Hours out of range: " + hoursOffset);
    }
    if (minutesOffset < -59 || minutesOffset > 59) {
        throw new IllegalArgumentException("Minutes out of range: " + minutesOffset);
    }
    if (hoursOffset > 0 && minutesOffset < 0) {
        throw new IllegalArgumentException("Positive hours with negative minutes not supported");
    }
    if (hoursOffset < 0 && minutesOffset > 0) {
        minutesOffset = -minutesOffset;
    }
    int totalMinutes = hoursOffset * 60 + minutesOffset;
    if (totalMinutes < -23 * 60 - 59 || totalMinutes > 23 * 60 + 59) {
        throw new IllegalArgumentException("Offset is too large");
    }
    int offset = 0;
    try {
        offset = FieldUtils.safeMultiply(totalMinutes, DateTimeConstants.MILLIS_PER_MINUTE);
    } catch (ArithmeticException ex) {
        throw new IllegalArgumentException("Offset is too large");
    }
    return forOffsetMillis(offset);
}
