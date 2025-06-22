static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node instanceof Element) {
        Element el = (Element) node;
        if (el.tag.preserveWhitespace())
            return true;
        else if (el.parent() != null) // Corrected the logical flow to check parent
            return el.parent().tag.preserveWhitespace();
    }
    return false;
}
