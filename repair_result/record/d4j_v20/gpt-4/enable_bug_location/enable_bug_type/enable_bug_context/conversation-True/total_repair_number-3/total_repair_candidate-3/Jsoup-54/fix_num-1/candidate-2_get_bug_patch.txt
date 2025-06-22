private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
        String key = attribute.getKey();
        // Remove invalid first characters (must start with letter, underscore or colon)
        key = key.replaceAll("^[^a-zA-Z_:]+", "");
        // Remove invalid characters thereafter (only allow - a-zA-Z0-9 _ : .)
        key = key.replaceAll("[^-a-zA-Z0-9_:.]", "");
        if (!key.isEmpty()) {
            el.setAttribute(key, attribute.getValue());
        }
    }
}
