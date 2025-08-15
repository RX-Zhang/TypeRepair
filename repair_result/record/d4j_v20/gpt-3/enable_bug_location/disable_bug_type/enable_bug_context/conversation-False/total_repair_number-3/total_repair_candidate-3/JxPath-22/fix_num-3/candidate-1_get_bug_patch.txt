public static String getNamespaceURI(Node node) {
    if (node instanceof Document) {
        node = ((Document) node).getDocumentElement();
    }

    Element element = (Element) node;

    String uri = element.getNamespaceURI();
    if (uri == null) {
        // For elements without a namespace URI, check for default namespace declarations 
        // or prefixed namespace declarations on this element or its ancestors.
        String prefix = getPrefix(node);

        Node aNode = node;
        while (aNode != null && aNode.getNodeType() == Node.ELEMENT_NODE) {
            Element aElement = (Element) aNode;
            if (prefix == null) {
                String defaultNamespace = aElement.getAttribute("xmlns");
                if (defaultNamespace != null && !defaultNamespace.isEmpty()) {
                    return defaultNamespace;
                }
            } else {
                String qname = "xmlns:" + prefix;
                String ns = aElement.getAttribute(qname);
                if (ns != null && !ns.isEmpty()) {
                    return ns;
                }
            }
            aNode = aNode.getParentNode();
        }
        return null;
    }
    return uri;
}
