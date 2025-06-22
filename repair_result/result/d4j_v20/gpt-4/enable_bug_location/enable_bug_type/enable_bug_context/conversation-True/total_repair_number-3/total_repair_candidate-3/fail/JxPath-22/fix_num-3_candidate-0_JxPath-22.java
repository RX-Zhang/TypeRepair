public static String getNamespaceURI(Node node) {
    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    Element element = (Element) node;

    String uri = element.getNamespaceURI();
    if (uri == null) {
        String prefix = getPrefix(node);
        if (prefix == null) {
            // Check for default namespace declaration
            Node aNode = node;
            while (aNode != null && aNode.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) aNode;
                if (el.hasAttribute("xmlns") && !el.getAttribute("xmlns").isEmpty()) {
                    return el.getAttribute("xmlns");
                }
                aNode = aNode.getParentNode();
            }
            return null;
        }

        String qname = "xmlns:" + prefix;

        Node aNode = node;
        while (aNode != null) {
            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                Attr attr = ((Element) aNode).getAttributeNode(qname);
                if (attr != null && !attr.getValue().isEmpty()) {
                    return attr.getValue();
                }
            }
            aNode = aNode.getParentNode();
        }
        return null;
    }
    return uri;
}
