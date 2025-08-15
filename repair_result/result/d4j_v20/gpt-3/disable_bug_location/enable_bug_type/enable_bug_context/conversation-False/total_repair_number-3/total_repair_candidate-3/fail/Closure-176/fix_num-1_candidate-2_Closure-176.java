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
      left.setJSType(isVarDeclaration || leftType == null ? resultType : null);

      if (var != null && var.isTypeInferred()) {
        JSType oldType = var.getType();
        // To avoid type mismatch issues, ensure that the types involved are compatible.
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
        // Guard against possible null leftType to avoid type mismatch errors.
        scope.inferQualifiedSlot(left, qualifiedName,
            leftType == null ? unknownType : leftType,
            resultType);
      }

      left.setJSType(resultType);
      ensurePropertyDefined(left, resultType);
      break;
  }
}
