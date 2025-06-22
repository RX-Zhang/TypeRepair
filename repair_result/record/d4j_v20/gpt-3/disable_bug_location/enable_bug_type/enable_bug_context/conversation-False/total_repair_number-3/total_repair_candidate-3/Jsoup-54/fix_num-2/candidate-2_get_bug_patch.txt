private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
        String originalKey = attribute.getKey();
        // Remove invalid characters from the key
        String key = originalKey.replaceAll("[^-a-zA-Z0-9_:.]", "");
        // Ensure key is not empty and starts with a valid character (a-zA-Z_:)
        if (key.isEmpty() || !key.substring(0, 1).matches("[a-zA-Z_:]")) {
            // skip invalid attribute name that cannot be converted to valid XML attribute name
            continue;
        }
        try {
            el.setAttribute(key, attribute.getValue());
        } catch (DOMException e) {
            // Skip attributes with invalid characters causing DOM exceptions
            // or handle logging if needed
        }
    }
}
