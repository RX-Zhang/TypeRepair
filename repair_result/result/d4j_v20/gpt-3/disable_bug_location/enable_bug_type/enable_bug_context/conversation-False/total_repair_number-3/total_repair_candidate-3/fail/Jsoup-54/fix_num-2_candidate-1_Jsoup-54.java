private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
        String rawKey = attribute.getKey();
        // Remove invalid characters from the start and replace invalid characters inside key
        // First, remove leading characters until a valid start char is found
        int i = 0;
        for (; i < rawKey.length(); i++) {
            char c = rawKey.charAt(i);
            if ( (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_' || c == ':' ) {
                break;
            }
        }
        if (i == rawKey.length()) {
            // No valid start character found, skip this attribute
            continue;
        }
        String key = rawKey.substring(i);
        // Now replace invalid characters in the rest of the key
        key = key.replaceAll("[^-a-zA-Z0-9_:.]", "");

        // Finally, if key is empty or still invalid, skip setting it to avoid DOMException
        if (key.isEmpty()) continue;

        try {
            el.setAttribute(key, attribute.getValue());
        } catch (DOMException e) {
            // Skip attributes with invalid characters that still cause exceptions
        }
    }
}
