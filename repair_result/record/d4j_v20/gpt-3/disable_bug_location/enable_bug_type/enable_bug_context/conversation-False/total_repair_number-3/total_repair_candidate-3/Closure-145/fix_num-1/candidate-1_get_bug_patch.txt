private boolean isOneExactlyFunctionOrDo(Node n) {
    // For labels with block children, we need to ensure that a
    // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
    // are skipped.

    if (n.getType() == Token.LABEL) {
      // If the node is a label, check if exactly one child and that child
      // is a FUNCTION or DO node.
      if (n.getChildCount() == 1) {
        Node child = n.getFirstChild();
        return (child.getType() == Token.FUNCTION || child.getType() == Token.DO);
      } else {
        // Label with no child or multiple children cannot be FUNCTION or DO exactly.
        return false;
      }
    }
    // Otherwise, check if the node itself is FUNCTION or DO.
    return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
}
