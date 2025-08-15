private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped.
    // Only an empty statement or a block with more than one child,
    // or a block whose only child is a FUNCTION or DO, qualifies.
    if (n.getType() == Token.FUNCTION || n.getType() == Token.DO) {
        return true;
    }

    if (n.getType() == Token.BLOCK) {
        int count = getNonEmptyChildCount(n, 2);
        return count == 1 && isOneExactlyFunctionOrDo(getFirstNonEmptyChild(n));
    }

    return false;
}
