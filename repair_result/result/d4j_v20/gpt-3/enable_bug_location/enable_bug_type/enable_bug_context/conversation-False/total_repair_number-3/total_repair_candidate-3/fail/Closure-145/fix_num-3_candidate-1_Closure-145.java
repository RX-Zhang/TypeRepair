private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped. 
    // Either an empty statement or a block with more than one child,
    // way it isn't a FUNCTION or DO.
    if (n.getType() == Token.BLOCK) {
        // If block count is exactly 1 and that child is FUNCTION or DO,
        // return true. Otherwise false.
        int count = getNonEmptyChildCount(n, 2);
        if (count == 1) {
            Node onlyChild = getFirstNonEmptyChild(n);
            int type = onlyChild.getType();
            return (type == Token.FUNCTION || type == Token.DO);
        }
        return false;
    }
    return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
}
