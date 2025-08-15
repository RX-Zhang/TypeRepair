public static String getNamespaceURI(Node node) {
    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    // Handle case where node might not be an Element (e.g., attribute, text, etc.)
    if (node.getNodeType() != Node.ELEMENT_NODE) {
        return null;
    }

    Element element = (Element) node;

    String uri = element.getNamespaceURI();
    if (uri == null) {
        String prefix = getPrefix(node);
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
