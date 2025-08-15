@Override public void visit(NodeTraversal t, Node n, Node parent) {
  if (n == scope.getRootNode()) return;

  if (n.getType() == Token.LP && n.getParent() == scope.getRootNode()) {
    handleFunctionInputs(n);
    return;
  }

  attachLiteralTypes(n);
  switch (n.getType()) {
    case Token.FUNCTION:
      if (parent.getType() == Token.NAME) {
        // Functions with a NAME parent are declarations. We need to define them.
        defineDeclaredFunction(n, parent);
        return;
      }
      defineDeclaredFunction(n, parent);
      break;
    case Token.CATCH:
      defineCatch(n, parent);
      break;
    case Token.VAR:
      defineVar(n, parent);
      break;
  }
}
