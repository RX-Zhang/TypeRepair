@Override public void visit(NodeTraversal t, Node n, Node parent) {
  if (n == scope.getRootNode()) return;

  // The check should be if n is a FUNCTION node and parent is the root,
  // meaning it's a function expression immediately under the root.
  if (n.getType() == Token.FUNCTION && parent == scope.getRootNode()) {
    handleFunctionInputs(n);
    return;
  }

  attachLiteralTypes(n);
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
