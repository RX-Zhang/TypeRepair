private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // skipped. 
    // Either it's an empty statement or a block with more than one child,
    // then it isn't a FUNCTION or DO.
    if (n.getType() == Token.FUNCTION || n.getType() == Token.DO) {
      return true;
    }

    // Sometimes the node can be a BLOCK with exactly one child that is FUNCTION or DO.
    if (n.getType() == Token.BLOCK) {
      int count = getNonEmptyChildCount(n, 2);
      if (count == 1) {
        Node firstAndOnlyChild = getFirstNonEmptyChild(n);
        return firstAndOnlyChild != null && 
               (firstAndOnlyChild.getType() == Token.FUNCTION || firstAndOnlyChild.getType() == Token.DO);
      }
    }

    return false;
}
