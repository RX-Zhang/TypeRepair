private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped. 
    // Either an empty statement or a block with more than one child,
    // means it isn't a FUNCTION or DO.
    if (n.isLabel()) {
        Node child = n.getFirstChild();
        if (child != null && child.isBlock() && getNonEmptyChildCount(child, 2) != 1) {
            return false;
        }
        n = child;
    }
    return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
}
