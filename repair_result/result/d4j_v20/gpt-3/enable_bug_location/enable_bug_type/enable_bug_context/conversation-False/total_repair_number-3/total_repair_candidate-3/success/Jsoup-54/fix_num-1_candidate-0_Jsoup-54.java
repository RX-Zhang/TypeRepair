private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // Validate XML attribute names
        String key = attribute.getKey();
        if (key.matches("^[a-zA-Z_:][-a-zA-Z0-9_:.]*$")) {
            el.setAttribute(key, attribute.getValue());
        } else {
            // Handle invalid attribute names according to your needs
            // For example, you could log a warning or skip the attribute
            System.out.println("Invalid attribute name: " + key);
        }
    }
}
