static boolean preserveWhitespace(Node node) {
    // looks only at this element and five levels up, to prevent recursion & needless stack searches
    if (node != null && node instanceof Element) {
        Element el = (Element) node;
        Element current = el;
        for (int i = 0; i < 6 && current != null; i++) { // check up to 6 levels: itself + 5 ancestors
            if (current.tag().preserveWhitespace())
                return true;
            current = current.parent();
        }
    }
    return false;
}
