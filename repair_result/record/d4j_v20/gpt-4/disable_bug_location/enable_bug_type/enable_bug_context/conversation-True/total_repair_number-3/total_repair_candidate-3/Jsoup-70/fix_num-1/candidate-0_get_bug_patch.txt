static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    int levels = 0;
    while (node != null && node instanceof Element && levels < 6) {
        Element el = (Element) node;
        if (el.tag().preserveWhitespace())
            return true;
        node = el.parent();
        levels++;
    }
    return false;
}
