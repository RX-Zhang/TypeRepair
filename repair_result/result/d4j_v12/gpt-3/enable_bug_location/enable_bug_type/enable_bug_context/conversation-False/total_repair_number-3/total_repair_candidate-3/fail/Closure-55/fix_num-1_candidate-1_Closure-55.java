private static boolean isReduceableFunctionExpression(Node n) {
  if (n == null) {
    return false;
  }
  return NodeUtil.isFunctionExpression(n);
}
