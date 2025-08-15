static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node != null && node instanceof Element) {
        Element el = (Element) node;
        // Corrected logical flow to ensure that we check the parent elements correctly
        if (el.tag.preserveWhitespace())
            return true;
        // Check if the parent exists and if it preserves whitespace
        Node parent = el.parent();
        while (parent != null) {
            if (parent instanceof Element && ((Element) parent).tag.preserveWhitespace()) {
                return true;
            }
            parent = parent.parent();
        }
    }
    return false;
}
