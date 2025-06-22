private boolean isFoldableExpressBlock(Node n) {
  if (n.getType() == Token.BLOCK) {
    if (n.hasOneChild()) {
      Node maybeExpr = n.getFirstChild();
      // We only have to worry about methods with an implicit 'this'
      // param, or this doesn't happen.
      return NodeUtil.isExpressionNode(maybeExpr) 
          && !NodeUtil.isExprResult(maybeExpr); // Prevent folding EXPR_RESULTs
    }
  }

  return false;
}
