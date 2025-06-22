public static String getNamespaceURI(Node node) {
    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
        // If it's an attribute node, the namespace URI should be retrieved directly.
        return node.getNamespaceURI();
    }

    Element element;
    if (node.getNodeType() == Node.ELEMENT_NODE) {
        element = (Element) node;
    } else {
        // For other node types, namespace URI is usually null or inherited,
        // attempt to get namespace URI of parent element
        Node parent = node.getParentNode();
        if (parent != null && parent.getNodeType() == Node.ELEMENT_NODE) {
            element = (Element) parent;
        } else {
            return null;
        }
    }

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
