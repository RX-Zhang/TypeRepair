public int parseInto(ReadWritableInstant instant, String text, int position) {
    DateTimeParser parser = requireParser();
    if (instant == null) {
        throw new IllegalArgumentException("Instant must not be null");
    }

    Chronology chrono = instant.getChronology();
    chrono = selectChronology(chrono);
    int defaultYear = getDefaultYear();
    if (defaultYear == 0) {
        // If no default year is set, fall back to the current year from chronology and instant
        long instantMillis = instant.getMillis();
        long instantLocal = instantMillis + chrono.getZone().getOffset(instantMillis);
        defaultYear = chrono.year().get(instantLocal);
    }

    DateTimeParserBucket bucket = new DateTimeParserBucket(
        instant.getMillis(), chrono, iLocale, iPivotYear, defaultYear);
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
