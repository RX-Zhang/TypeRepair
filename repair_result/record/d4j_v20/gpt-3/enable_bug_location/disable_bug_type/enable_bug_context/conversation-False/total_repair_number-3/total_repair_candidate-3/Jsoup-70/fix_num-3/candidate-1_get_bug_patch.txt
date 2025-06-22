static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node != null && node instanceof Element) {
        Element el = (Element) node;
        if (el.tag().preserveWhitespace())
            return true;
        Element parent = el.parent();
        if (parent != null && parent.tag().preserveWhitespace())
            return true;
    }
    return false;
}
