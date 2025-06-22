private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]*
        String key = attribute.getKey();
        // Remove invalid characters from the attribute name to prevent XML exceptions
        key = key.replaceAll("[^a-zA-Z0-9_:\\-\\.]", "");

        if (key.isEmpty()) {
            // skip invalid attribute names that result in empty keys after sanitization
            continue;
        }

        try {
            el.setAttribute(key, attribute.getValue());
        } catch (DOMException e) {
            // ignore or log attributes that cannot be set due to invalid characters
            // e.g. INVALID_CHARACTER_ERR
        }
    }
}
