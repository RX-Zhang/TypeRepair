public int parseInto(ReadWritableInstant instant, String text, int position) {
    DateTimeParser parser = requireParser();
    if (instant == null) {
        throw new IllegalArgumentException("Instant must not be null");
    }
    
    long instantMillis = instant.getMillis();
    Chronology chrono = instant.getChronology();
    long instantLocal = instantMillis + chrono.getZone().getOffset(instantMillis);
    chrono = selectChronology(chrono);
    
    // Validate the input text for proper month and day values
    String[] parts = text.split(" ");
    if (parts.length != 2) {
        throw new IllegalArgumentException("Text must contain month and day");
    }
    
    int month;
    int day;
    try {
        month = Integer.parseInt(parts[0]);
        day = Integer.parseInt(parts[1]);
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Month and day must be valid integers");
    }
    
    // Check if the day is valid for the given month and year
    if (month < 1 || month > 12 || day < 1 || day > 31) {
        throw new IllegalArgumentException("Month must be in the range [1,12] and day must be in the range [1,31]");
    }
    
    int maxDay = chrono.dayOfMonth().getMaximumValue(month, defaultYear);
    if (day > maxDay) {
        throw new IllegalArgumentException("Value " + day + " for dayOfMonth must be in the range [1," + maxDay + "]");
    }
    
    int defaultYear = chrono.year().get(instantLocal);
    
    DateTimeParserBucket bucket = new DateTimeParserBucket(
        instantLocal, chrono, iLocale, iPivotYear, defaultYear);
    int newPos = parser.parseInto(bucket, text, position);
    instant.setMillis(bucket.computeMillis(false, text));
    if (iOffsetParsed && bucket.getOffsetInteger() != null) {
        int parsedOffset = bucket.getOffsetInteger();
        DateTimeZone parsedZone = DateTimeZone.forOffsetMillis(parsedOffset);
        chrono = chrono.withZone(parsedZone);
    } else if (bucket.getZone() != null) {
        chrono = chrono.withZone(bucket.getZone());
    }
    instant.setChronology(chrono);
    if (iZone != null) {
        instant.setZone(iZone);
    }
    return newPos;
}
