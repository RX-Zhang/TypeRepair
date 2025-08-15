private boolean isSafeReplacement(Node node, Node replacement) {
  // No checks are needed for simple names.
  if (node.isName()) {
    return true;
  }
  Preconditions.checkArgument(node.isGetProp());

  Node obj = node.getFirstChild();
  if (obj.isName() && isNameAssignedTo(obj.getString(), replacement)) {
    return false;
  }

  return true;
}
