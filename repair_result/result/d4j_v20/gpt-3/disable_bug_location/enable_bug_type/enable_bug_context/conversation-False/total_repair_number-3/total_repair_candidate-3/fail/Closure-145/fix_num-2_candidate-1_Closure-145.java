private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped. 
    // Either an empty statement or a block with more than one child,
    // way it isn't a FUNCTION or DO.
    // We want to wrap FUNCTION and DO statements in blocks for compatibility.
    return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
}
