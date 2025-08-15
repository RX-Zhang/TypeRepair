private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        // valid xml attribute names are: ^[a-zA-Z_:][-a-zA-Z0-9_:.]*
        // Replace invalid characters with empty string, but ensure it starts with a valid start char
        String key = attribute.getKey();

        // Remove invalid characters
        key = key.replaceAll("[^-a-zA-Z0-9_:.]", "");

        // If the first character is not a letter or underscore or colon, prefix with underscore
        if (!key.isEmpty() && !key.substring(0,1).matches("[a-zA-Z_:]")) {
            key = "_" + key;
        }

        if (!key.isEmpty()) {
            el.setAttribute(key, attribute.getValue());
        }
        // else skip attribute with empty key after cleanup
    }
}
