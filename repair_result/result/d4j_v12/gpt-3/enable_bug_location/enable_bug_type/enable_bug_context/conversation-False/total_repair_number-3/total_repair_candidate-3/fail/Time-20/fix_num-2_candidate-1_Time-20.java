public int parseInto(DateTimeParserBucket bucket, String text, int position) {
    String str = text.substring(position);

    // To avoid partial matches (e.g., "America/Dawson" matching before "America/Dawson_Creek"),
    // we sort ALL_IDS by length descending to try longer IDs first.
    // Assuming ALL_IDS is a List or array - if not, wrap it accordingly.

    // If ALL_IDS is an unsorted collection, create a sorted list here:
    List<String> sortedIds = new ArrayList<>(ALL_IDS);
    sortedIds.sort((a, b) -> b.length() - a.length());

    for (String id : sortedIds) {
        if (str.startsWith(id)) {
            bucket.setZone(DateTimeZone.forID(id));
            return position + id.length();
        }
    }
    return ~position;
}
