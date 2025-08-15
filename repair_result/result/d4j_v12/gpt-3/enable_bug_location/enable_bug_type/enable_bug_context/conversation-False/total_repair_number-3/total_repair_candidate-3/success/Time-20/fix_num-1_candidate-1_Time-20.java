public int parseInto(DateTimeParserBucket bucket, String text, int position) {
    String str = text.substring(position);
    String match = null;
    for (String id : ALL_IDS) {
        if (str.startsWith(id)) {
            if (match == null || id.length() > match.length()) {
                match = id;
            }
        }
    }
    if (match != null) {
        bucket.setZone(DateTimeZone.forID(match));
        return position + match.length();
    }
    return ~position;
}
