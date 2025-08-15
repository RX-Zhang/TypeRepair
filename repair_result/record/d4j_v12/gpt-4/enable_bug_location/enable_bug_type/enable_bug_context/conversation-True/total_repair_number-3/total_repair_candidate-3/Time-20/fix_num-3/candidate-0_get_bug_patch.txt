public int parseInto(DateTimeParserBucket bucket, String text, int position) {
    String str = text.substring(position);
    String matchedId = null;
    for (String id : ALL_IDS) {
        if (str.startsWith(id)) {
            if (matchedId == null || id.length() > matchedId.length()) {
                matchedId = id;
            }
        }
    }
    if (matchedId != null) {
        bucket.setZone(DateTimeZone.forID(matchedId));
        return position + matchedId.length();
    }
    return ~position;
}
