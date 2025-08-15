private void findCalledFunctions(
    Node node, Set<String> changed) {
  Preconditions.checkArgument(changed != null);
  // For each referenced function, add a new reference
  if (node.getType() == Token.CALL) {
    Node child = node.getFirstChild();
    if (child != null && child.getType() == Token.NAME) {
      changed.add(child.getString());
    } else if (child != null && child.getType() == Token.GETPROP) {
      // In case of object method calls, add the method name, for example obj.method()
      Node last = child.getLastChild();
      if (last != null && last.getType() == Token.NAME) {
        changed.add(last.getString());
      }
    }
  }

  for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
    findCalledFunctions(c, changed);
  }
}
