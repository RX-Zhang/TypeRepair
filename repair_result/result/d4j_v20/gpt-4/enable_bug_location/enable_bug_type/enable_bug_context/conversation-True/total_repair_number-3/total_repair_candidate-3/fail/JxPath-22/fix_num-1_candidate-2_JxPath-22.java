public static String getNamespaceURI(Node node) {
    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    Element element = (Element) node;

    String uri = element.getNamespaceURI();
    if (uri == null) {
        String prefix = getPrefix(node);
        if (XMLNS_NAMESPACE_URI.equals(prefix)) {
            return XMLNS_NAMESPACE_URI;
        }
        String qname = prefix == null ? "xmlns" : "xmlns:" + prefix;

        Node aNode = node;
        while (aNode != null && aNode.getNodeType() == Node.ELEMENT_NODE) {
            Attr attr = ((Element) aNode).getAttributeNode(qname);
            if (attr != null) {
                return attr.getValue();
            }
            aNode = aNode.getParentNode();
        }
        return null;
    }
    return uri;
}
