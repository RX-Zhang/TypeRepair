private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // Validate the attribute key against XML naming rules
        String key = attribute.getKey();
        if (!key.matches("^[a-zA-Z_:][-a-zA-Z0-9_:.]*")) {
            // Skip invalid attribute names
            continue;
        }
        el.setAttribute(key, attribute.getValue());
    }
}
