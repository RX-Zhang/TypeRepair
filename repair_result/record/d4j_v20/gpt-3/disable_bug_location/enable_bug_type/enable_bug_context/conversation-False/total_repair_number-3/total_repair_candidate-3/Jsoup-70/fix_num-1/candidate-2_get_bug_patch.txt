static boolean preserveWhitespace(Node node) {
    // looks only at this element and up to five levels up, to prevent recursion & needless stack searches
    int depth = 0;
    while (node != null && node instanceof Element && depth < 6) {
        Element el = (Element) node;
        if (el.tag.preserveWhitespace())
            return true;
        node = el.parent();
        depth++;
    }
    return false;
}
