public static String getNamespaceURI(Node node) {
    if (node == null) {
        throw new IllegalArgumentException("Node cannot be null");
    }

    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    if (!(node instanceof Element)) {
        throw new IllegalArgumentException("Node must be an instance of Element");
    }

    Element element = (Element) node;

    String uri = element.getNamespaceURI();
    if (uri == null) {
        String prefix = getPrefix(node);
        String qname = prefix == null ? "xmlns" : "xmlns:" + prefix;

        Node aNode = node;
        while (aNode != null) {
            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                Attr attr = ((Element) aNode).getAttributeNode(qname);
                if (attr != null) {
                    return attr.getValue();
                }
            }
            aNode = aNode.getParentNode();
        }
        return null;
    }
    return uri;
}
