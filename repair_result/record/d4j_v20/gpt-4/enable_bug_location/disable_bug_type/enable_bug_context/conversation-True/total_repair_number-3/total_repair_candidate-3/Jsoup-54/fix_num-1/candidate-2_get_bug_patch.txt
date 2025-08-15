private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names must follow: ^[a-zA-Z_:][-a-zA-Z0-9_:.]*
        String key = attribute.getKey();
        // If the attribute key is invalid for XML, do not set it to avoid DOMException
        if (key.matches("^[a-zA-Z_:][-a-zA-Z0-9_:.]*$")) {
            el.setAttribute(key, attribute.getValue());
        }
    }
}
