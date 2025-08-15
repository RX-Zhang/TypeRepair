private void findAliases(NodeTraversal t) {
  Scope scope = t.getScope();
  for (Var v : scope.getVarIterable()) {
    Node n = v.getNode();
    int type = n.getType();
    Node parent = n.getParent();
    if (parent != null && parent.isVar()) {
      if (n.hasChildren() && n.getFirstChild().isQualifiedName()) {
        String name = n.getString();
        Var aliasVar = scope.getVar(name);
        if (aliasVar == null) {
          report(t, n, GOOG_SCOPE_NON_ALIAS_LOCAL, name);
          continue;
        }
        if (aliasVar.getInitialValue() == null) {
          report(t, n, GOOG_SCOPE_NON_ALIAS_LOCAL, name);
          continue;
        }
        Node initialValue = aliasVar.getInitialValue();
        if (initialValue == null || !initialValue.isQualifiedName()) {
          report(t, n, GOOG_SCOPE_NON_ALIAS_LOCAL, name);
          continue;
        }
        String qualifiedName = initialValue.getQualifiedName();

        aliases.put(name, aliasVar);
        transformation.addAlias(name, qualifiedName);
      } else {
        // TODO(robbyw): Support using locals for private variables.
        report(t, n, GOOG_SCOPE_NON_ALIAS_LOCAL, n.getString());
      }
    }
  }
}
