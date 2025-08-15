private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped. 
    // Either an empty statement or a block with more than one child,
    // isn't a FUNCTION or DO.
    return n != null && (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
}
