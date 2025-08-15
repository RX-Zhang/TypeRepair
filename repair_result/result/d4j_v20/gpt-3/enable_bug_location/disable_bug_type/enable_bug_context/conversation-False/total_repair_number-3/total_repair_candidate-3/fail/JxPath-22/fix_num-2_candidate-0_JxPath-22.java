public static String getNamespaceURI(Node node) {
    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    if (node.getNodeType() != Node.ELEMENT_NODE) {
        return null;
    }

    Element element = (Element) node;

    String uri = element.getNamespaceURI();
    if (uri == null) {
        String prefix = getPrefix(node);
        String qname = prefix == null || prefix.isEmpty() ? "xmlns" : "xmlns:" + prefix;

        Node aNode = node;
        while (aNode != null) {
            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) aNode;
                String attrValue = el.getAttribute(qname);
                if (attrValue != null && !attrValue.isEmpty()) {
                    return attrValue;
                }
            }
            aNode = aNode.getParentNode();
        }
        return null;
    }
    return uri;
}
