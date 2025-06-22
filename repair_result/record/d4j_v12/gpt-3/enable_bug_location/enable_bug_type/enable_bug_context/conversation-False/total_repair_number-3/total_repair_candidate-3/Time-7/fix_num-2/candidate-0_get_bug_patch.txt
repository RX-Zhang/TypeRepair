public int parseInto(ReadWritableInstant instant, String text, int position) {
    DateTimeParser parser = requireParser();
    if (instant == null) {
        throw new IllegalArgumentException("Instant must not be null");
    }
    
    long instantMillis = instant.getMillis();
    Chronology chrono = instant.getChronology();
    long instantLocal = instantMillis + chrono.getZone().getOffset(instantMillis);
    chrono = selectChronology(chrono);
    int defaultYear = chrono.year().get(instantLocal);
    
    DateTimeParserBucket bucket = new DateTimeParserBucket(
        instantLocal, chrono, iLocale, iPivotYear, defaultYear);
    
    // Validate input text for month and day
    if (text == null || text.isEmpty()) {
        throw new IllegalArgumentException("Text must not be null or empty");
    }
    
    int newPos = parser.parseInto(bucket, text, position);
    
    // Check if the parsed day is valid for the given month and year
    if (bucket.getDay() != null && bucket.getMonth() != null) {
        int day = bucket.getDay();
        int month = bucket.getMonth();
        if (day < 1 || day > 31 || (month == 2 && day > 29)) {
            throw new IllegalArgumentException("Invalid day for the given month");
        }
    }
    
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
