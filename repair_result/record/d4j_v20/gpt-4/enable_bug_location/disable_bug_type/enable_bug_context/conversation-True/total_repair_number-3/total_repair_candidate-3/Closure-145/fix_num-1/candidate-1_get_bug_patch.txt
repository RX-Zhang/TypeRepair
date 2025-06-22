private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped. 
    // Either an empty statement or a block with more than one child,
    // way it isn't a FUNCTION or DO.
    if (n == null) {
        return false;
    }

    if (n.getType() == Token.FUNCTION || n.getType() == Token.DO) {
        return true;
    }

    // If this is a labeled statement, check if its child is a FUNCTION or DO
    if (n.getType() == Token.LABEL) {
        Node child = n.getFirstChild();
        if (child != null && (child.getType() == Token.FUNCTION || child.getType() == Token.DO)) {
            return true;
        }
    }

    return false;
}
