private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped.
    // This method should only return true if n itself is a FUNCTION or DO node,
    // or if it's a LABEL node whose only child is a FUNCTION or DO node.
    int type = n.getType();
    if (type == Token.FUNCTION || type == Token.DO) {
        return true;
    } else if (type == Token.LABEL) {
        Node child = getFirstNonEmptyChild(n);
        if (child != null) {
            int childType = child.getType();
            return (childType == Token.FUNCTION || childType == Token.DO);
        }
    }
    return false;
}
