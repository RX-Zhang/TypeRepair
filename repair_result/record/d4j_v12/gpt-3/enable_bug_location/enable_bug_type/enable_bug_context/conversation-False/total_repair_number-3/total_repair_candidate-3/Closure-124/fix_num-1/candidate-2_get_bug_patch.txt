private boolean isSafeReplacement(Node node, Node replacement) {
  // No checks are needed for simple names.
  if (node.isName()) {
    return true;
  }
  Preconditions.checkArgument(node.isGetProp());

  Node lhs = node.getFirstChild();
  if (lhs.isName() && isNameAssignedTo(lhs.getString(), replacement)) {
    return false;
  }

  return true;
}
