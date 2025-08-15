private void handleObjectLit(NodeTraversal t, Node n) {
  for (Node child = n.getFirstChild();
      child != null;
      child = child.getNext()) {
    // Maybe STRING, GET, SET

    // We should never see a mix of numbers and strings.
    String name = child.getString();
    
    // Ensure type is not null to prevent potential NullPointerException
    T type = typeSystem.getType(getScope(), n, name);
    
    // Convert type to a valid type if necessary
    if (type == null) {
      // Handle the case where type is null before proceeding
      continue;
    }

    Property prop = getProperty(name);
    if (!prop.scheduleRenaming(child,
                               processProperty(t, prop, type, null))) {
      // TODO(user): It doesn't look like the user can do much in this
      // case right now.
      if (propertiesToErrorFor.containsKey(name)) {
        compiler.report(JSError.make(
            t.getSourceName(), child, propertiesToErrorFor.get(name),
            Warnings.INVALIDATION, name,
            type.toString(), n.toString(), ""));
      }
    }
  }
}
