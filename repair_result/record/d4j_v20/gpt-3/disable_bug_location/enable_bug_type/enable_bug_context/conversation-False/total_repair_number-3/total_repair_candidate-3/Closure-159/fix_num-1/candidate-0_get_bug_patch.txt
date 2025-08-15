private void findCalledFunctions(
    Node node, Set<String> changed) {
  Preconditions.checkArgument(changed != null);
  // For each referenced function, add a new reference
  if (node.isCall()) {
    Node firstChild = node.getFirstChild();
    if (firstChild != null && firstChild.isName()) {
      changed.add(firstChild.getString());
    }
  }

  for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
    findCalledFunctions(c, changed);
  }
}
