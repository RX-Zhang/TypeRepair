public static boolean testNode(Node node, NodeTest test) {
    if (test == null) {
        return true;
    }
    if (test instanceof NodeNameTest) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return false;
        }

        NodeNameTest nodeNameTest = (NodeNameTest) test;
        QName testName = nodeNameTest.getNodeName();
        String namespaceURI = nodeNameTest.getNamespaceURI();
        boolean wildcard = nodeNameTest.isWildcard();
        String testPrefix = testName.getPrefix();

        // Fix logical error: wildcard with a null prefix should check namespaceUri matching rather than always returning true
        if (wildcard) {
            // If wildcard node name, check namespace match
            String nodeNS = DOMNodePointer.getNamespaceURI(node);
            return equalStrings(namespaceURI, nodeNS);
        }

        // Check name equality and namespace equality when not a wildcard
        if (testName.getName().equals(DOMNodePointer.getLocalName(node))) {
            String nodeNS = DOMNodePointer.getNamespaceURI(node);
            return equalStrings(namespaceURI, nodeNS);
        }

        return false;
    }
    if (test instanceof NodeTypeTest) {
        int nodeType = node.getNodeType();
        switch (((NodeTypeTest) test).getNodeType()) {
            case Compiler.NODE_TYPE_NODE:
                return nodeType == Node.ELEMENT_NODE
                        || nodeType == Node.DOCUMENT_NODE;
            case Compiler.NODE_TYPE_TEXT:
                return nodeType == Node.CDATA_SECTION_NODE
                    || nodeType == Node.TEXT_NODE;
            case Compiler.NODE_TYPE_COMMENT:
                return nodeType == Node.COMMENT_NODE;
            case Compiler.NODE_TYPE_PI:
                return nodeType == Node.PROCESSING_INSTRUCTION_NODE;
        }
        return false;
    }
    if (test instanceof ProcessingInstructionTest) {
        if (node.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE) {
            String testPI = ((ProcessingInstructionTest) test).getTarget();
            String nodePI = ((ProcessingInstruction) node).getTarget();
            return testPI.equals(nodePI);
        }
    }
    return false;
}
