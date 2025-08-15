static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node == null || !(node instanceof Element))
        return false;

    Element el = (Element) node;
    if (el.tag.preserveWhitespace())
        return true;

    int levels = 5;
    Element parent = el.parent();
    while (parent != null && levels-- > 0) {
        if (parent.tag.preserveWhitespace())
            return true;
        parent = parent.parent();
    }
    return false;
}
