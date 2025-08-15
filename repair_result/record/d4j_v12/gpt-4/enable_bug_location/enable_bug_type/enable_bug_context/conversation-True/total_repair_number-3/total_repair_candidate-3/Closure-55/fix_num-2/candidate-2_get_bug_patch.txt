private static boolean isReduceableFunctionExpression(Node n) {
  return NodeUtil.isFunctionDeclaration(n) || NodeUtil.isFunctionExpression(n);
}
