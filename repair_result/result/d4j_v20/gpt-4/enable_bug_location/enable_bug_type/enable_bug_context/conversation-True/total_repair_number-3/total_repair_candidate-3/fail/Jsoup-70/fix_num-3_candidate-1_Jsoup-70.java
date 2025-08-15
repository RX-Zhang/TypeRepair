static boolean preserveWhitespace(Node node) {
    if (node != null && node instanceof Element) {
        Element el = (Element) node;
        if (el.tag().preserveWhitespace())
            return true;
        Element parent = el.parent();
        if (parent != null && parent.tag().preserveWhitespace())
            return true;
    }
    return false;
}
