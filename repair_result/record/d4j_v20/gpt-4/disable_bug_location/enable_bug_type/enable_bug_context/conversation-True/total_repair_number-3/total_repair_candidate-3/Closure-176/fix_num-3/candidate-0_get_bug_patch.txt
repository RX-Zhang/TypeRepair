private void updateScopeForTypeChange(
    FlowScope scope, Node left, JSType leftType, JSType resultType) {
  Preconditions.checkNotNull(resultType);
  switch (left.getType()) {
    case Token.NAME:
      String varName = left.getString();
      Var var = syntacticScope.getVar(varName);
      boolean isVarDeclaration = left.hasChildren();

      boolean isVarTypeBetter = !isVarDeclaration || var == null || var.isTypeInferred();

      if (isVarTypeBetter) {
        redeclareSimpleVar(scope, left, resultType);
      }
      // Check if leftType is assignable to resultType before setting to avoid type mismatch
      if (isVarDeclaration || leftType == null || !leftType.isSubtype(resultType)) {
        left.setJSType(resultType);
      } else {
        left.setJSType(null);
      }

      if (var != null && var.isTypeInferred()) {
        JSType oldType = var.getType();
        // Safely get least supertype only if oldType is not null
        if (oldType == null) {
          var.setType(resultType);
        } else {
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
  }
}
