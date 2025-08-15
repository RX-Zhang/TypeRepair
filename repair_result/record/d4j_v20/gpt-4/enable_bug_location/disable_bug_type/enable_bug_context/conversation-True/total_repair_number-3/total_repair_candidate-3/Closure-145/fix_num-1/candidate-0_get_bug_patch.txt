private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped. 
    // An empty statement or a block with more than one child 
    // means it isn't a FUNCTION or DO.
    if (n.getType() == Token.BLOCK) {
      int childCount = getNonEmptyChildCount(n, 2);
      if (childCount != 1) {
        return false;
      }
      n = getFirstNonEmptyChild(n);
    }
    return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
}
