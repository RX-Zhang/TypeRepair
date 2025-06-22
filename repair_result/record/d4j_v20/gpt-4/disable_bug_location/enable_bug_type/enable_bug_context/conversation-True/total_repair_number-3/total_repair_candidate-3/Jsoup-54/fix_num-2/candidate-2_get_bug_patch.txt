private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
        String originalKey = attribute.getKey();
        String sanitizedKey = originalKey.replaceAll("[^-a-zA-Z0-9_:.]", "");
        
        // Validate that sanitizedKey matches the allowed XML attribute name pattern and is not empty
        if (sanitizedKey.isEmpty() || !sanitizedKey.matches("^[a-zA-Z_:][-a-zA-Z0-9_:.]*$")) {
            // Skip invalid attribute names that cannot be converted to valid XML attribute names
            continue;
        }
        
        try {
            el.setAttribute(sanitizedKey, attribute.getValue());
        } catch (DOMException e) {
            // In case setting attribute fails (e.g. invalid name), ignore or log as needed
            // For now, just continue without setting the attribute
            continue;
        }
    }
}
