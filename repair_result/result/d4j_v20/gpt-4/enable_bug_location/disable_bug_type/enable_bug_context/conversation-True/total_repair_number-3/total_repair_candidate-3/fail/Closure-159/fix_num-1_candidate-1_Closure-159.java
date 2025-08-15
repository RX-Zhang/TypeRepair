private void findCalledFunctions(
    Node node, Set<String> changed) {
  Preconditions.checkArgument(changed != null);
  // For each referenced function, add a new reference
  if (node.getType() == Token.CALL) {
    Node child = node.getFirstChild();
    if (child != null && child.getType() == Token.NAME) {
      changed.add(child.getString());
    }
  }

  for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
    // Avoid double-counting the same call node
    if (c != null && c != node.getFirstChild()) {
      findCalledFunctions(c, changed);
    } else if (c == null) {
      break;
    }
  }
}
