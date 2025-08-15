private void findCalledFunctions(
    Node node, Set<String> changed) {
  Preconditions.checkArgument(changed != null);
  // For each referenced function, add a new reference
  if (node.getType() == Token.CALL) {
    Node child = node.getFirstChild();
    // Check for CALL on property or direct NAME reference
    if (child.getType() == Token.NAME) {
      changed.add(child.getString());
    } else if (child.getType() == Token.GETPROP) {
      Node nameNode = child.getLastChild();
      if (nameNode != null && nameNode.getType() == Token.NAME) {
        changed.add(nameNode.getString());
      }
    }
  }

  for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
    findCalledFunctions(c, changed);
  }
}
