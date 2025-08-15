private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]*
        String originalKey = attribute.getKey();

        // Sanitize the key by removing invalid characters for XML attribute names
        // but preserve valid characters like letters, digits, '_', ':', '.', and '-'
        StringBuilder sanitizedKey = new StringBuilder();
        for (int i = 0; i < originalKey.length(); i++) {
            char c = originalKey.charAt(i);
            if ((i == 0 && (Character.isLetter(c) || c == '_' || c == ':')) ||
                (i > 0 && (Character.isLetterOrDigit(c) || c == '_' || c == ':' || c == '.' || c == '-'))) {
                sanitizedKey.append(c);
            }
            // else skip invalid characters
        }

        String key = sanitizedKey.toString();

        if (key.isEmpty()) {
            // skip attributes that have no valid key after sanitization
            continue;
        }

        try {
            el.setAttribute(key, attribute.getValue());
        } catch (DOMException e) {
            // Skip attributes that cause DOM exceptions due to invalid characters
            // Or optionally, log the attribute that was skipped
        }
    }
}
