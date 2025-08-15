private boolean isFoldableExpressBlock(Node n) {
  if (n.getType() == Token.BLOCK) {
    if (n.hasOneChild()) {
      Node maybeExpr = n.getFirstChild();
      // IE has a bug where event handlers behave differently when
      // their return value is used vs. when their return value is in
      // an EXPR_RESULT. It's pretty freaking weird. See:
      // http://code.google.com/p/closure-compiler/issues/detail?id=291
      // We try to detect this case, and not fold EXPR_RESULTs
      // into other expressions.

      // We only have to worry about methods with an implicit 'this'
      // param, or this doesn't happen.

      if (NodeUtil.isExpressionNode(maybeExpr)) {
        // Special check for event handlers named 'onchange' or 'bonchange'.
        // Avoid folding when the expression is a call like f.onchange()
        if (maybeExpr.isExprResult()) {
          Node call = maybeExpr.getFirstChild();
          if (call != null && call.isCall()) {
            Node callee = call.getFirstChild();
            if (callee != null && callee.isGetProp()) {
              String propName = callee.getLastChild().getString();
              if ("onchange".equals(propName) || "bonchange".equals(propName)) {
                return false;
              }
            }
          }
        }
        return true;
      }
    }
  }

  return false;
}
