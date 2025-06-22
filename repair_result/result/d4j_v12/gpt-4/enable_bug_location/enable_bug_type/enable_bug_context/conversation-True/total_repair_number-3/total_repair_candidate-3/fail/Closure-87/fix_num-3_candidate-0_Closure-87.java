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

      // Detect the case where expression is a call with a method on the
      // same object as the condition, which can cause problems in IE.

      if (NodeUtil.isExpressionNode(maybeExpr)) {
        if (maybeExpr.getType() == Token.EXPR_RESULT) {
          Node call = maybeExpr.getFirstChild();
          if (call != null && call.getType() == Token.CALL) {
            Node callee = call.getFirstChild();
            if ((callee != null)
                && (callee.getType() == Token.GETPROP || callee.getType() == Token.GETELEM)) {
              // Avoid folding if the object of the call is same as the condition
              // which is the node 'n' parent or some other scenario that could cause the IE bug.
              // Here we conservatively return false.
              return false;
            }
          }
        }
        return true;
      }
    }
  }

  return false;
}
