public int parseInto(ReadWritableInstant instant, String text, int position) {
    DateTimeParser parser = requireParser();
    if (instant == null) {
        throw new IllegalArgumentException("Instant must not be null");
    }
    
    Chronology chrono = instant.getChronology();
    chrono = selectChronology(chrono);

    int defaultYear = getDefaultYear();  // Use the formatter's default year if set
    if (defaultYear == 0) {
        long instantMillis = instant.getMillis();
        long instantLocal = instantMillis + chrono.getZone().getOffset(instantMillis);
        defaultYear = chrono.year().get(instantLocal);
    }
    
    DateTimeParserBucket bucket = new DateTimeParserBucket(
        0L, chrono, iLocale, iPivotYear, defaultYear); // Use zero base millis
    
    int newPos = parser.parseInto(bucket, text, position);
    long computedMillis = bucket.computeMillis(false, text);

    if (computedMillis == DateTimeParserBucket.INVALID_MILLIS) {
        return ~position;
    }

    // Set the millis first before getting updated chronology and zone information
    instant.setMillis(computedMillis);

    // Update chronology and zone if offset / zone info was parsed
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
