private static boolean isReduceableFunctionExpression(Node n) {
  return NodeUtil.isFunction(n) && n.isFunctionExpression();
}
