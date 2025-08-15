private boolean isPrototypePropertyAssign(Node assign) {
  Node n = assign.getFirstChild();
  if (n != null && NodeUtil.isVarOrSimpleAssignLhs(n, assign)
      && n.getType() == Token.GETPROP
      ) {
    // We want to exclude the assignment itself from the usage list
    boolean isChainedProperty =
        n.getFirstChild() != null &&
        n.getFirstChild().getType() == Token.GETPROP;

    if (isChainedProperty) {
      Node firstChild = n.getFirstChild();
      Node secondChild = firstChild.getFirstChild();
      Node child = (secondChild != null) ? secondChild.getNext() : null;

      if (child != null &&
          child.getType() == Token.STRING &&
          child.getString().equals("prototype")) {
        return true;
      }
    }
  }

  return false;
}
