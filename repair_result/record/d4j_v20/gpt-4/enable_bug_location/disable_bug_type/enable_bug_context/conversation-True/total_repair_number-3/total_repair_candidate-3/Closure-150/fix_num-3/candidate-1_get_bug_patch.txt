@Override public void visit(NodeTraversal t, Node n, Node parent) {
  if (n == scope.getRootNode()) return;

  if (n.getType() == Token.LP && parent == scope.getRootNode()) {
    handleFunctionInputs(parent);
    return;
  }

  attachLiteralTypes(n);

  // Handle goog.foo style property declarations
  if (n.getType() == Token.EXPR_RESULT && n.getFirstChild() != null) {
    Node expr = n.getFirstChild();
    if (expr.getType() == Token.NAME) {
      // foo;
      defineVar(expr, n);
    } else if (expr.getType() == Token.GETPROP) {
      Node owner = getPrototypePropertyOwner(expr);
      if (owner != null) {
        defineVar(owner, n);
      }
    }
  }

  switch (n.getType()) {
    case Token.FUNCTION:
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
