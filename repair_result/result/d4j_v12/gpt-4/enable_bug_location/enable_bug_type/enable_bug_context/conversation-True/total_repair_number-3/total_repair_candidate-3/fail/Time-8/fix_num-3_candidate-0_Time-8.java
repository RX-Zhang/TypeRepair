public static DateTimeZone forOffsetHoursMinutes(int hoursOffset, int minutesOffset) throws IllegalArgumentException {
    if (hoursOffset == 0 && minutesOffset == 0) {
        return DateTimeZone.UTC;
    }
    if (hoursOffset < -23 || hoursOffset > 23) {
        throw new IllegalArgumentException("Hours out of range: " + hoursOffset);
    }
    int effectiveMinutesOffset = minutesOffset;
    if (hoursOffset < 0) {
        if (minutesOffset > 0) {
            effectiveMinutesOffset = -minutesOffset;
        } else if (minutesOffset < 0) {
            effectiveMinutesOffset = minutesOffset;
        } else {
            effectiveMinutesOffset = 0;
        }
    } else {
        if (minutesOffset < 0) {
            effectiveMinutesOffset = -minutesOffset;
        }
    }
    if (effectiveMinutesOffset < -59 || effectiveMinutesOffset > 59) {
        throw new IllegalArgumentException("Minutes out of range: " + minutesOffset);
    }
    int totalMinutes = hoursOffset * 60 + effectiveMinutesOffset;
    try {
        int offset = FieldUtils.safeMultiply(totalMinutes, DateTimeConstants.MILLIS_PER_MINUTE);
        return forOffsetMillis(offset);
    } catch (ArithmeticException ex) {
        throw new IllegalArgumentException("Offset is too large");
    }
}
