static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node != null && node instanceof Element) {
        Element el = (Element) node;
        if (el.tag().preserveWhitespace()) {
            return true;
        }
        Element parent = el.parent();
        int depth = 0;
        while (parent != null && depth < 5) {
            if (parent.tag().preserveWhitespace()) {
                return true;
            }
            parent = parent.parent();
            depth++;
        }
    }
    return false;
}
