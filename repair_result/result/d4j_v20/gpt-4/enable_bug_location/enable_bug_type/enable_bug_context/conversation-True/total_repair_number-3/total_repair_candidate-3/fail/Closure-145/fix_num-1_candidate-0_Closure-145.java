private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped. 
    // Either an empty statement or a block with more than one child,
    // means it isn't a FUNCTION or DO.
    if (n == null) {
        return false;
    }
    int type = n.getType();
    return type == Token.FUNCTION || type == Token.DO;
}
