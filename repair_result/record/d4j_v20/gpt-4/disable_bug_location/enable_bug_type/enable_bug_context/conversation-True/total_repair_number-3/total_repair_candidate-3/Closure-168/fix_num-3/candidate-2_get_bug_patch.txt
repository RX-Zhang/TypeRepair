@Override public void visit(NodeTraversal t, Node n, Node parent) {
  if (t.inGlobalScope()) {
    return;
  }

  if (n.isReturn() && n.getFirstChild() != null) {
    data.get(t.getScopeRoot()).recordNonEmptyReturn();
  }

  if (t.getScopeDepth() <= 2) {
    // The first-order function analyzer looks at two types of variables:
    //
    // 1) Local variables that are assigned in inner scopes ("escaped vars")
    //
    // 2) Local variables that are assigned more than once.
    //
    // We treat all global variables as escaped by default, so there's
    // no reason to do this extra computation for them.
    return;
  }

  // Retrieve function analysis results that include type information
  AstFunctionContents functionContents = getFunctionAnalysisResults(t.getScopeRoot());
  
  if (n.isName() && NodeUtil.isLValue(n) &&
      // Be careful of bleeding functions, which create variables
      // in the inner scope, not the scope where the name appears.
      !NodeUtil.isBleedingFunctionName(n)) {
    String name = n.getString();
    Scope scope = t.getScope();
    Var var = scope.getVar(name);
    if (var != null) {
      Scope ownerScope = var.getScope();

      // Verify that the variable type matches expected type if available
      if (functionContents != null) {
        JSType expectedType = functionContents.getVarType(name);
        JSType actualType = var.getType();
        if (expectedType != null && actualType != null
            && !expectedType.isUnknownType() && !actualType.isUnknownType()
            && !actualType.isSubtype(expectedType)) {
          // Report a type mismatch error or handle accordingly
          compiler.report(JSError.make(n, typeParsingErrorReporter.getErrorManager(),
              typeParsingErrorReporter.getWarningLevel(),
              "Expected type " + expectedType + " but found " + actualType));
        }
      }

      if (ownerScope.isLocal()) {
        data.get(ownerScope.getRootNode()).recordAssignedName(name);
      }

      if (scope != ownerScope && ownerScope.isLocal()) {
        data.get(ownerScope.getRootNode()).recordEscapedVarName(name);
      }
    }
  } else if (n.isGetProp() && n.isUnscopedQualifiedName() &&
      NodeUtil.isLValue(n)) {
    String name = NodeUtil.getRootOfQualifiedName(n).getString();
    Scope scope = t.getScope();
    Var var = scope.getVar(name);
    if (var != null) {
      Scope ownerScope = var.getScope();
      if (scope != ownerScope && ownerScope.isLocal()) {
        data.get(ownerScope.getRootNode())
            .recordEscapedQualifiedName(n.getQualifiedName());
      }
    }
  }
}
