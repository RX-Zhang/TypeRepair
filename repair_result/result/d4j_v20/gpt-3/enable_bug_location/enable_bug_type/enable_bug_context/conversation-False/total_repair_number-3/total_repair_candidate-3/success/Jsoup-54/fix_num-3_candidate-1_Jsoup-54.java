private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // Validate the attribute name according to XML rules
        String key = attribute.getKey();
        if (key.matches("^[a-zA-Z_:][-a-zA-Z0-9_:.]*$")) {
            el.setAttribute(key, attribute.getValue());
        } else {
            // Optionally log or handle invalid attribute names
            System.err.println("Invalid attribute name: " + key);
        }
    }
}
