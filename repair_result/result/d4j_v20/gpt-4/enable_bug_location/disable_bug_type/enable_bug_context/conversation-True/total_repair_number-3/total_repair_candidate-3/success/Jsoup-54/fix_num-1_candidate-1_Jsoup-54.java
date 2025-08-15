private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
        // Instead of stripping invalid characters which can cause missing or duplicate attributes,
        // skip attributes with invalid names to avoid XML DOM exceptions.
        String key = attribute.getKey();
        if (key.matches("^[a-zA-Z_:][-a-zA-Z0-9_:.]*$")) {
            el.setAttribute(key, attribute.getValue());
        }
    }
}
