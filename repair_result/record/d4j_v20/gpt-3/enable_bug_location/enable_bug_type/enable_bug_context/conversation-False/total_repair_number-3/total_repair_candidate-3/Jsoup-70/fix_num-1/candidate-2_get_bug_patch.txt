static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node != null && node instanceof Element) {
        Element el = (Element) node;
        // Correcting the logic to ensure proper whitespace preservation
        if (el.tag.preserveWhitespace())
            return true;
        // Check if parent exists and if it preserves whitespace
        Element parent = el.parent();
        while (parent != null) {
            if (parent.tag.preserveWhitespace())
                return true;
            parent = parent.parent();
        }
    }
    return false;
}
