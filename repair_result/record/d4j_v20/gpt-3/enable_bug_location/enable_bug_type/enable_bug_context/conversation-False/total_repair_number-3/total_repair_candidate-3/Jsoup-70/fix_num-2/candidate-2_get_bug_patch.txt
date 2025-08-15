static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node instanceof Element) {
        Element el = (Element) node;
        // Corrected the logic to ensure that if the element or any of its ancestors preserve whitespace, true is returned
        if (el.tag.preserveWhitespace())
            return true;
        while (el.parent() != null) {
            el = (Element) el.parent();
            if (el.tag.preserveWhitespace())
                return true;
        }
    }
    return false;
}
