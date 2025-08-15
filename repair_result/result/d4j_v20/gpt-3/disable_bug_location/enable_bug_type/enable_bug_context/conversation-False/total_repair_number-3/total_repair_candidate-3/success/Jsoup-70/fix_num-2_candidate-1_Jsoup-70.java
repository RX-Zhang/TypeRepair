static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node != null && node instanceof Element) {
        Element el = (Element) node;
        int depth = 0;
        while (el != null && depth <= 5) {
            if (el.tag.preserveWhitespace())
                return true;
            el = el.parent();
            depth++;
        }
    }
    return false;
}
