private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        String key = attribute.getKey();
        // Validate if the key matches the XML attribute name pattern: ^[a-zA-Z_:][-a-zA-Z0-9_:.]*
        if (key.matches("^[a-zA-Z_:][-a-zA-Z0-9_:.]*$")) {
            el.setAttribute(key, attribute.getValue());
        }
    }
}
