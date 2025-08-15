public static String getNamespaceURI(Node node) {
    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    Element element = (Element) node;

    String uri = element.getNamespaceURI();
    if (uri == null) {
        String prefix = getPrefix(node);
        String qname = prefix == null ? "xmlns" : "xmlns:" + prefix;

        Node aNode = node;
        while (aNode != null && aNode.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) aNode;
            if (e.hasAttribute(qname)) {
                return e.getAttribute(qname);
            }
            aNode = aNode.getParentNode();
        }
        return null;
    }
    return uri;
}
