private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]*
        // Replace invalid characters in key with empty string
        String key = attribute.getKey().replaceAll("[^a-zA-Z0-9_:\\-\\.]", "");
        // XML attribute names cannot start with digits or dots/hyphens, so prefix if necessary
        if (!key.isEmpty() && !key.substring(0,1).matches("[a-zA-Z_:]")) {
            key = "_" + key;
        }
        if (!key.isEmpty()) {
            el.setAttribute(key, attribute.getValue());
        }
        // if key is empty after cleanup, skip adding attribute
    }
}
