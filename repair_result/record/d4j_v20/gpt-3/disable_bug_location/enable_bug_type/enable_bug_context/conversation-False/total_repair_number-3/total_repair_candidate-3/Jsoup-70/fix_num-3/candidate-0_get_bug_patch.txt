static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    Node current = node;
    int depth = 0;
    while (current != null && depth < 6) {
        if (current instanceof Element) {
            Element el = (Element) current;
            if (el.tag.preserveWhitespace())
                return true;
        }
        current = current.parent();
        depth++;
    }
    return false;
}
