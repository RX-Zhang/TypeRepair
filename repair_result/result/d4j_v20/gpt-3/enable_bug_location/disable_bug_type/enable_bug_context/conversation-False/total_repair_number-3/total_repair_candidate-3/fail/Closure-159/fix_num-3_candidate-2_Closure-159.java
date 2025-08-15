private void findCalledFunctions(
    Node node, Set<String> changed) {
  Preconditions.checkArgument(changed != null);
  // For each referenced function, add a new reference
  if (node.getType() == Token.CALL) {
    Node child = node.getFirstChild();
    if (child.getType() == Token.NAME) {
      changed.add(child.getString());
    } else if (child.getType() == Token.GETPROP) {
      // If it's a method call like obj.method(), get the property name
      Node nameNode = child.getLastChild();
      if (nameNode != null && nameNode.getType() == Token.STRING) {
        changed.add(nameNode.getString());
      }
    }
  }

  for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
    findCalledFunctions(c, changed);
  }
}
