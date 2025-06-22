static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    int levels = 0;
    Node current = node;
    while (current != null && levels < 6) {
        if (current instanceof Element) {
            Element el = (Element) current;
            if (el.tag.preserveWhitespace())
                return true;
        }
        current = current.parentNode;
        levels++;
    }
    return false;
}
