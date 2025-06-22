static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node != null && node instanceof Element) {
        Element el = (Element) node;
        // Check if the current element preserves whitespace
        if (el.tag.preserveWhitespace()) {
            return true;
        }
        // Check if the parent element preserves whitespace
        Node parent = el.parent();
        while (parent != null && !(parent instanceof Document)) {
            if (((Element) parent).tag.preserveWhitespace()) {
                return true;
            }
            parent = parent.parent();
        }
    }
    return false;
}
