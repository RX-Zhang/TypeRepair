private void findCalledFunctions(
    Node node, Set<String> changed) {
  Preconditions.checkArgument(changed != null);
  // For each referenced function, add a new reference
  if (node.getType() == Token.CALL) {
    Node child = node.getFirstChild();
    if (child.getType() == Token.NAME) {
      // Only add function names that are not properties (e.g., foo.bar)
      if (child.getNext() == null || child.getNext().getType() != Token.GETPROP) {
        changed.add(child.getString());
      }
    }
  }

  for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
    findCalledFunctions(c, changed);
  }
}
