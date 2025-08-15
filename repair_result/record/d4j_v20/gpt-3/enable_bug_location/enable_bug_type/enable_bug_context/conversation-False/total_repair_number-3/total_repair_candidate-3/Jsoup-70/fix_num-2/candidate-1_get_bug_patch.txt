static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node instanceof Element) {
        Element el = (Element) node;
        if (el.tag.preserveWhitespace())
            return true;
        // Check if the parent is not null and if the parent's tag preserves whitespace
        Node parent = el.parent();
        while (parent != null && !(parent instanceof Element)) {
            parent = parent.parent(); // Move up the tree to find the nearest Element parent
        }
        return parent != null && ((Element) parent).tag.preserveWhitespace();
    }
    return false;
}
