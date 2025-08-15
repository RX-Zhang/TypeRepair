private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // Validate the attribute name according to XML standards
        String key = attribute.getKey();
        if (key.matches("^[a-zA-Z_:][-a-zA-Z0-9_:.]*$")) {
            el.setAttribute(key, attribute.getValue());
        } else {
            // Handle invalid attribute names appropriately
            // For example, we can log a warning or skip setting the attribute
            System.err.println("Invalid attribute name: " + key);
        }
    }
}
