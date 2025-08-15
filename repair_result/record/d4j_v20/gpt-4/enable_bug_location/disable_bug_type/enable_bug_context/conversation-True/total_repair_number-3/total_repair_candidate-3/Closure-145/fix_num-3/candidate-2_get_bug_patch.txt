private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped.
    // Either an empty statement or a block with more than one child,
    // way it isn't a FUNCTION or DO.
    if (n.getType() == Token.FUNCTION || n.getType() == Token.DO) {
      return true;
    }
    // Check the case where n is a label with a single child that is a FUNCTION or DO.
    if (n.getType() == Token.LABEL) {
      Node child = getFirstNonEmptyChild(n);
      if (child != null) {
        return (child.getType() == Token.FUNCTION || child.getType() == Token.DO);
      }
    }
    return false;
}
