public static String getNamespaceURI(Node node) {
    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    if (node.getNodeType() != Node.ELEMENT_NODE) {
        // For nodes other than elements, namespace URI can be retrieved directly
        return node.getNamespaceURI();
    }

    Element element = (Element) node;

    String uri = element.getNamespaceURI();
    if (uri == null) {
        String prefix = getPrefix(node);
        String qname = prefix == null || prefix.isEmpty() ? "xmlns" : "xmlns:" + prefix;

        Node aNode = node;
        while (aNode != null && aNode.getNodeType() == Node.ELEMENT_NODE) {
            Attr attr = ((Element) aNode).getAttributeNode(qname);
            if (attr != null) {
                String value = attr.getValue();
                if (value != null && !value.isEmpty()) {
                    return value;
                }
            }
            aNode = aNode.getParentNode();
        }
        return null;
    }
    return uri;
}
