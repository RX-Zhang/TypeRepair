private boolean isPrototypePropertyAssign(Node assign) {
  Node n = assign.getFirstChild();
  if (n != null && NodeUtil.isVarOrSimpleAssignLhs(n, assign)
      && n.getType() == Token.GETPROP
      ) {
    // We want to exclude the assignment itself from the usage list
    boolean isChainedProperty =
        n.getFirstChild().getType() == Token.GETPROP;

    if (isChainedProperty) {
      Node firstChild = n.getFirstChild();
      Node child = firstChild.getLastChild();

      if (firstChild.getSecondChild() != null &&
          firstChild.getSecondChild().getType() == Token.STRING &&
          firstChild.getSecondChild().getString().equals("prototype")) {
        return true;
      }
    }
  }

  return false;
}
