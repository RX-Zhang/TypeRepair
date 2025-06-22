private static boolean isReduceableFunctionExpression(Node n) {
  if (n == null) {
    return false;
  }
  try {
    return NodeUtil.isFunctionExpression(n);
  } catch (Exception e) {
    return false;
  }
}
