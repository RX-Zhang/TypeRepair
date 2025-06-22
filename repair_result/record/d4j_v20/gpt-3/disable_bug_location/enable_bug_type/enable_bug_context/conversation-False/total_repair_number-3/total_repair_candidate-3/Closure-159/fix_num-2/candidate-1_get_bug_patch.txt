private void findCalledFunctions(
    Node node, Set<String> changed) {
  Preconditions.checkArgument(changed != null);
  // For each referenced function, add a new reference
  if (node.getType() == Token.CALL) {
    Node callee = node.getFirstChild();
    if (callee.getType() == Token.NAME) {
      changed.add(callee.getString());
    } else if (callee.getType() == Token.GETPROP) {
      // Inline call to functions assigned via property access as well, i.e. $.fn.multicheck
      // Add the full qualified name as string to changed.
      String fullName = getQualifiedName(callee);
      if (fullName != null) {
        changed.add(fullName);
      }
    }
  }

  for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
    findCalledFunctions(c, changed);
  }
}

// Helper method to get a qualified name string from a GETPROP node.
private String getQualifiedName(Node getprop) {
  if (getprop == null || getprop.getType() != Token.GETPROP) {
    return null;
  }
  Node obj = getprop.getFirstChild();
  Node prop = obj.getNext();

  String objName = null;
  if (obj.getType() == Token.NAME) {
    objName = obj.getString();
  } else if (obj.getType() == Token.GETPROP) {
    objName = getQualifiedName(obj);
  } else {
    return null;
  }

  if (prop.getType() != Token.STRING && prop.getType() != Token.NAME) {
    return null;
  }

  return objName + "." + prop.getString();
}
