static boolean preserveWhitespace(Node node) {
    // looks only at this element and its parent, to prevent recursion & needless stack searches
    if (node != null && node instanceof Element) {
        Element el = (Element) node;
        if (el.tag().preserveWhitespace())
            return true;
        else {
            Element parent = el.parent();
            return parent != null && parent.tag().preserveWhitespace();
        }
    }
    return false;
}
