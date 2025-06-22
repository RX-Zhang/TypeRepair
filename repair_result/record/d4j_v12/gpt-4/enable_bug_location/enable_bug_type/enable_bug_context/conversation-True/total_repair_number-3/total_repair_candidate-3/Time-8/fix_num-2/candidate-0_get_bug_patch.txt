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
    // Adjust sign of minutesOffset if hoursOffset is negative, else ensure minutesOffset is positive
    int totalMinutes;
    if (hoursOffset < 0) {
        if (minutesOffset > 0) {
            minutesOffset = -minutesOffset;
        }
        totalMinutes = hoursOffset * 60 + minutesOffset;
    } else {
        if (minutesOffset < 0) {
            minutesOffset = -minutesOffset;
        }
        totalMinutes = hoursOffset * 60 + minutesOffset;
    }
    if (totalMinutes < -23 * 60 - 59 || totalMinutes > 23 * 60 + 59) {
        throw new IllegalArgumentException("Offset is too large");
    }
    int offset = totalMinutes * DateTimeConstants.MILLIS_PER_MINUTE;
    return forOffsetMillis(offset);
}
