private void updateScopeForTypeChange(
    FlowScope scope, Node left, JSType leftType, JSType resultType) {
  Preconditions.checkNotNull(resultType);
  switch (left.getType()) {
    case Token.NAME:
      String varName = left.getString();
      Var var = syntacticScope.getVar(varName);
      boolean isVarDeclaration = left.hasChildren();

      // When looking at VAR initializers for declared VARs, we tend
      // to use the declared type over the type it's being
      // initialized to in the global scope.
      //
      // For example,
      // /** @param {number} */ var f = goog.abstractMethod;
      // it's obvious that the programmer wants you to use
      // the declared function signature, not the inferred signature.
      //
      // Or,
      // /** @type {Object.<string>} */ var x = {};
      // the one-time anonymous object on the right side
      // is as narrow as it can possibly be, but we need to make
      // sure we back-infer the <string> element constraint on
      // the left hand side, so we use the left hand side.

      boolean isVarTypeBetter = !isVarDeclaration || var == null || var.isTypeInferred();
          // Makes it easier to check for NPEs.

      // Add type check before redeclareSimpleVar to avoid null varType
      if (isVarTypeBetter) {
        redeclareSimpleVar(scope, left, resultType);
      }
      // Defensive type check and set type appropriately
      if (isVarDeclaration || leftType == null) {
        left.setJSType(resultType);
      } else {
        left.setJSType(null);
      }

      if (var != null && var.isTypeInferred()) {
        JSType oldType = var.getType();
        // Defensive null checks on oldType and resultType before getLeastSupertype
        if (oldType == null) {
          var.setType(resultType);
        } else if (resultType != null) {
          var.setType(oldType.getLeastSupertype(resultType));
        }
      }
      break;
    case Token.GETPROP:
      String qualifiedName = left.getQualifiedName();
      if (qualifiedName != null) {
        scope.inferQualifiedSlot(left, qualifiedName,
            leftType == null ? unknownType : leftType,
            resultType);
      }

      left.setJSType(resultType);
      ensurePropertyDefined(left, resultType);
      break;
    default:
      // No action for other types
      break;
  }
}
