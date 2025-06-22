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
      // Set the type only if this is a var declaration or no leftType is present
      left.setJSType(isVarDeclaration || leftType == null ?
          resultType : null);

      if (var != null && var.isTypeInferred()) {
        JSType oldType = var.getType();
        if (oldType == null) {
          var.setType(resultType);
        } else {
          // To avoid TypeMismatchError, ensure the types are compatible before using getLeastSupertype
          JSType newType = oldType.getLeastSupertype(resultType);
          var.setType(newType);
        }
      }
      break;
    case Token.GETPROP:
      String qualifiedName = left.getQualifiedName();
      if (qualifiedName != null) {
        // Defensive check to avoid passing incompatible types
        JSType inferLeftType = leftType == null ? unknownType : leftType;
        scope.inferQualifiedSlot(left, qualifiedName, inferLeftType, resultType);
      }

      left.setJSType(resultType);
      ensurePropertyDefined(left, resultType);
      break;
  }
}
