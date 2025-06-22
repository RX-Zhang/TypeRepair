public static String getNamespaceURI(Node node) {
    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    if (!(node instanceof Element)) {
        return null;
    }
    
    Element element = (Element) node;

    String uri = element.getNamespaceURI();
    if (uri == null) {
        String prefix = getPrefix(node);
        String qname = prefix == null ? "xmlns" : "xmlns:" + prefix;

        Node aNode = node;
        while (aNode != null && aNode.getNodeType() == Node.ELEMENT_NODE) {
            Element aElement = (Element) aNode;
            if (aElement.hasAttribute(qname)) {
                String value = aElement.getAttribute(qname);
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
