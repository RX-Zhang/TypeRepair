public int parseInto(ReadWritableInstant instant, String text, int position) {
    DateTimeParser parser = requireParser();
    if (instant == null) {
        throw new IllegalArgumentException("Instant must not be null");
    }
    
    Chronology chrono = instant.getChronology();
    if (chrono == null) {
        chrono = DateTimeUtils.getChronology(null);
    }
    chrono = selectChronology(chrono);
    int defaultYear = getDefaultYear();
    if (defaultYear == 0) {
        long instantMillis = instant.getMillis();
        int offset = chrono.getZone().getOffset(instantMillis);
        long instantLocal = instantMillis + offset;
        defaultYear = chrono.year().get(instantLocal);
    }
    
    DateTimeParserBucket bucket = new DateTimeParserBucket(
        instant.getMillis(), chrono, iLocale, iPivotYear, defaultYear);
    int newPos = parser.parseInto(bucket, text, position);
    if (newPos < 0) {
        return newPos;
    }
    Long millis = bucket.computeMillis(false, text);
    if (millis == null) {
        return ~position;
    }
    instant.setMillis(millis);
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
