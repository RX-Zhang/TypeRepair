private void findAliases(NodeTraversal t) {
  Scope scope = t.getScope();
  for (Var v : scope.getVarIterable()) {
    Node n = v.getNode();
    int type = n.getType();
    Node parent = n.getParent();
    if (parent.isVar()) {
      if (n.hasChildren() && n.getFirstChild().isQualifiedName()) {
        String name = n.getString();
        Var aliasVar = scope.getVar(name);
        if (aliasVar == null || aliasVar.getInitialValue() == null ||
            aliasVar.getInitialValue().getQualifiedName() == null) {
          report(t, n, GOOG_SCOPE_NON_ALIAS_LOCAL, name);
          continue;
        }
        String qualifiedName = aliasVar.getInitialValue().getQualifiedName();
        transformation.addAlias(name, qualifiedName);
      } else {
        // TODO(robbyw): Support using locals for private variables.
        report(t, n, GOOG_SCOPE_NON_ALIAS_LOCAL, n.getString());
      }
    }
  }
}
