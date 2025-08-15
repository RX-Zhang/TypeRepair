static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    Element el = node instanceof Element ? (Element) node : null;
    for (int i = 0; el != null && i < 6; i++) {
        if (el.tag.preserveWhitespace())
            return true;
        el = el.parent();
    }
    return false;
}
