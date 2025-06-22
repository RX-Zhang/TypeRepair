@Override public void visit(NodeTraversal t, Node n, Node parent) {
  if (n == scope.getRootNode()) return;

  if (n.getType() == Token.LP && parent == scope.getRootNode()) {
    handleFunctionInputs(n);
    return;
  }

  attachLiteralTypes(n);
  switch (n.getType()) {
    case Token.FUNCTION:
      // If the parent is a NAME node, the function is part of a declaration like "function foo() {}"
      // so let defineDeclaredFunction handle it, else if the parent is an ASSIGN or other node,
      // we should proceed.
      if (parent.getType() == Token.NAME) {
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
