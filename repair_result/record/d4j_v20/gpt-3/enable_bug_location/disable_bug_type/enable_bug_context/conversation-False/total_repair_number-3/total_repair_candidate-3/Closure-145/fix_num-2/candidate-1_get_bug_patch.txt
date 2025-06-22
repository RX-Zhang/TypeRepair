private boolean isOneExactlyFunctionOrDo(Node n) {
      // For labels with block children, we need to ensure that a
      // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
      // are skipped.
      // An empty statement or a block with more than one child,
      // means it isn't a FUNCTION or DO.
      if (n.getType() == Token.LABEL) {
        Node child = n.getFirstChild();
        return child != null && (child.getType() == Token.FUNCTION || child.getType() == Token.DO);
      }
      return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
}
