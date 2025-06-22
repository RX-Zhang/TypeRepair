@Override public void visit(NodeTraversal t, Node n, Node parent) {
  if (n == scope.getRootNode()) return;

  if (n.getType() == Token.LP && parent == scope.getRootNode()) {
    handleFunctionInputs(parent);
    return;
  }

  attachLiteralTypes(n);
  switch (n.getType()) {
    case Token.FUNCTION:
      if (parent.getType() == Token.NAME ||
          (parent.getType() == Token.ASSIGN && parent.getParent().getType() == Token.EXPR_RESULT)) {
        // The function is assigned to a property or namespace object, so
        // define a stub function type to collect its type signature.
        // This fixes the missing property issues in the test cases.
        defineStubFunctionType(n, parent);
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
