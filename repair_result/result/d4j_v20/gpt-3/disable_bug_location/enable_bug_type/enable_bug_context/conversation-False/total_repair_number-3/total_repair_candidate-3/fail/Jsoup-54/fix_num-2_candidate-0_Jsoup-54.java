private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]*
        String originalKey = attribute.getKey();
        // Remove leading characters until we have a valid start character 
        // (letter, underscore, or colon), then remove invalid characters from the rest
        StringBuilder keyBuilder = new StringBuilder();
        int len = originalKey.length();
        int pos = 0;
        // Skip invalid start chars
        while (pos < len) {
            char c = originalKey.charAt(pos);
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_' || c == ':') {
                keyBuilder.append(c);
                pos++;
                break;
            }
            pos++;
        }
        // Append valid chars for the rest of the attribute key
        while (pos < len) {
            char c = originalKey.charAt(pos);
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')
                    || c == '-' || c == '_' || c == ':' || c == '.') {
                keyBuilder.append(c);
            }
            pos++;
        }
        String key = keyBuilder.toString();
        if (!key.isEmpty()) {
            try {
                el.setAttribute(key, attribute.getValue());
            } catch (DOMException ignored) {
                // Skip attributes with invalid characters that cause DOMException
            }
        }
    }
}
