private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
    for (Attribute attribute : source.attributes()) {
        String key = attribute.getKey();
        try {
            el.setAttribute(key, attribute.getValue());
        } catch (DOMException e) {
            // skip invalid xml attribute names that cause INVALID_CHARACTER_ERR
        }
    }
}
