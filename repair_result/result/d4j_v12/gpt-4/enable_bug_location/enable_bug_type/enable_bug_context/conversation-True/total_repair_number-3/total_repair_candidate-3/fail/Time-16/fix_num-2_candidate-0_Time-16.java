public int parseInto(ReadWritableInstant instant, String text, int position) {
    DateTimeParser parser = requireParser();
    if (instant == null) {
        throw new IllegalArgumentException("Instant must not be null");
    }
    
    // Use initial chronology from formatter or from the instant
    Chronology chrono = instant.getChronology();
    chrono = selectChronology(chrono);

    // Use the local instant based on chronology zone and instant millis
    long instantMillis = instant.getMillis();
    long instantLocal = instantMillis + chrono.getZone().getOffset(instantMillis);

    DateTimeParserBucket bucket = new DateTimeParserBucket(
        instantLocal, chrono, iLocale, iPivotYear, iDefaultYear);
    int newPos = parser.parseInto(bucket, text, position);

    // Compute the millis from parsed values, using false for UTC
    long parsedMillis = bucket.computeMillis(false, text);
    // Set millis first
    instant.setMillis(parsedMillis);

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
