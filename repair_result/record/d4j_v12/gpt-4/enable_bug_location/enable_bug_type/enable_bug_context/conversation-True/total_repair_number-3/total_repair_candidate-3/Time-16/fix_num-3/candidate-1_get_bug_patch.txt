public int parseInto(ReadWritableInstant instant, String text, int position) {
    DateTimeParser parser = requireParser();
    if (instant == null) {
        throw new IllegalArgumentException("Instant must not be null");
    }
    
    long instantMillis = instant.getMillis();
    Chronology chrono = instant.getChronology();
    Integer pivotYear = getPivotYear();
    int defaultYear = getDefaultYear();
    DateTimeZone zone = getZone();

    chrono = selectChronology(chrono);
    
    DateTimeParserBucket bucket = new DateTimeParserBucket(
        instantMillis, chrono, iLocale, pivotYear, defaultYear);
    int newPos = parser.parseInto(bucket, text, position);
    long millis = bucket.computeMillis(false, text);
    instant.setMillis(millis);
    
    if (iOffsetParsed && bucket.getOffsetInteger() != null) {
        int parsedOffset = bucket.getOffsetInteger();
        DateTimeZone parsedZone = DateTimeZone.forOffsetMillis(parsedOffset);
        chrono = chrono.withZone(parsedZone);
    } else if (bucket.getZone() != null) {
        chrono = chrono.withZone(bucket.getZone());
    } else if (zone != null) {
        chrono = chrono.withZone(zone);
    }
    
    instant.setChronology(chrono);
    if (zone != null) {
        instant.setZone(zone);
    }
    return newPos;
}
