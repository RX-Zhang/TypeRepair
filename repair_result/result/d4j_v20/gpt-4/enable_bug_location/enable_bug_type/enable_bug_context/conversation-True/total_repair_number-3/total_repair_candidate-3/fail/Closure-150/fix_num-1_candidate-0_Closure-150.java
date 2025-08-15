@Override public void visit(NodeTraversal t, Node n, Node parent) {
  if (n == scope.getRootNode()) return;

  if (n.getType() == Token.LP && parent == scope.getRootNode()) {
    handleFunctionInputs(n);  // Use 'n' here instead of 'parent'
    return;
  }

  attachLiteralTypes(n);
  switch (n.getType()) {
    case Token.FUNCTION:
      if (parent.getType() == Token.LP) {
        // Function declared inside a call expression, handle as stub function input
        handleFunctionInputs(n);
        return;
      }
      if (parent.getType() == Token.NAME) {
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
