private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]
        String key = attribute.getKey();
        // Remove characters that are illegal in XML attribute names
        key = key.replaceAll("[^a-zA-Z0-9_:\\-\\.]", "");
        // If the key is empty after cleaning, skip to avoid DOMException
        if (key.isEmpty()) continue;
        el.setAttribute(key, attribute.getValue());
    }
}
