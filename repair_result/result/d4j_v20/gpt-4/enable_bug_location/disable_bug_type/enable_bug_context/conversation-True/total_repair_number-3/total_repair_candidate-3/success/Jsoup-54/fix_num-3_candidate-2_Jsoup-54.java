private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
        String key = attribute.getKey();
        // Remove characters not allowed in XML names, but preserve valid ones
        // Replace invalid characters with empty string only if they break XML rules
        // The first character must be [a-zA-Z_:], subsequent chars can be [-a-zA-Z0-9_:.]
        if (key.isEmpty() || !key.substring(0, 1).matches("[a-zA-Z_:]")) {
            // Invalid start character, remove all invalid chars to fallback to valid key
            key = key.replaceAll("[^a-zA-Z0-9_:.\\-]", "");
            // If still invalid or empty, skip this attribute to avoid DOMException
            if (key.isEmpty() || !key.substring(0, 1).matches("[a-zA-Z_:]")) {
                continue;
            }
        } else {
            // Clean subsequent characters only
            String firstChar = key.substring(0,1);
            String rest = key.substring(1).replaceAll("[^a-zA-Z0-9_:.\\-]", "");
            key = firstChar + rest;
        }
        el.setAttribute(key, attribute.getValue());
    }
}
