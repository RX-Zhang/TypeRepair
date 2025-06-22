private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
        String key = attribute.getKey();
        // Validate the attribute name to ensure it is a valid XML attribute name.
        if (!key.matches("^[a-zA-Z_:][-a-zA-Z0-9_:.]*$")) {
            // Skip attributes with invalid names to avoid DOMException
            continue;
        }
        el.setAttribute(key, attribute.getValue());
    }
}
