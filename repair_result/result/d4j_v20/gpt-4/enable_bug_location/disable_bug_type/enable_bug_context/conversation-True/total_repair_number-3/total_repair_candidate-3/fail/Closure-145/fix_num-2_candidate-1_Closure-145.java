private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped. 
    // Either an empty statement or a block with more than one child,
    // means it isn't a FUNCTION or DO.
    // Also, if this node is a LABEL, we should look deeper.
    if (n.getType() == Token.LABEL) {
        Node child = getFirstNonEmptyChild(n);
        return child != null && (child.getType() == Token.FUNCTION || child.getType() == Token.DO);
    }
    return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
}
