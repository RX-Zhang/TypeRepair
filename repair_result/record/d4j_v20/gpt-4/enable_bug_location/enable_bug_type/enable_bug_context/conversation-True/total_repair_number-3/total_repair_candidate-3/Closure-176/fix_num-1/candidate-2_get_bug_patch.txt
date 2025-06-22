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

      // Before setting JSType on left, check for null to avoid assigning incorrect type.
      if (isVarDeclaration || leftType == null) {
        left.setJSType(resultType);
      } else {
        left.setJSType(null);
      }

      if (var != null && var.isTypeInferred()) {
        JSType oldType = var.getType();
        if (oldType == null) {
          var.setType(resultType);
        } else {
          // Ensure oldType and resultType are not null before calling getLeastSupertype
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
