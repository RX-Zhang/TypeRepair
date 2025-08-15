private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
        // Remove invalid characters from the start of the key to ensure first char is valid
        // Also skip attributes with empty keys after sanitization to avoid DOMException
        String rawKey = attribute.getKey();

        // Sanitize key to match valid XML attribute name requirements
        // First character must be [a-zA-Z_:], subsequent characters can be [-a-zA-Z0-9_:.]
        // We'll filter character-by-character to build a valid attribute name
        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < rawKey.length(); i++) {
            char c = rawKey.charAt(i);
            if (i == 0) {
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == ':' || c == '_') {
                    keyBuilder.append(c);
                } 
                // else skip invalid first char
            } else {
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') ||
                    c == '-' || c == '_' || c == ':' || c == '.') {
                    keyBuilder.append(c);
                }
                // else skip invalid char
            }
        }
        String key = keyBuilder.toString();
        if (!key.isEmpty()) {
            try {
                el.setAttribute(key, attribute.getValue());
            } catch (DOMException ignore) {
                // Defensive: skip attributes that still cause DOMException for safety
            }
        }
    }
}
