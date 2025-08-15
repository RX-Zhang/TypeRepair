private void findCalledFunctions(
    Node node, Set<String> changed) {
  Preconditions.checkArgument(changed != null);
  // For each referenced function, add a new reference
  if (node.getType() == Token.CALL) {
    Node child = node.getFirstChild();
    if (child.getType() == Token.NAME) {
      changed.add(child.getString());
    } else if (child.getType() == Token.GETPROP) {
      // To handle calls like obj.fn(), add the property name
      Node prop = child.getLastChild();
      if (prop != null && prop.getType() == Token.NAME) {
        changed.add(prop.getString());
      }
    }
  }

  for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
    findCalledFunctions(c, changed);
  }
}
