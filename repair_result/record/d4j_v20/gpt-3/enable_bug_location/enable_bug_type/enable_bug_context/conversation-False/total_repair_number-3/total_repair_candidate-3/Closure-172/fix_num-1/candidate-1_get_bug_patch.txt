private boolean isQualifiedNameInferred(
    String qName, Node n, JSDocInfo info,
    Node rhsValue, JSType valueType) {
  if (valueType == null) {
    return true;
  }

  // Prototypes of constructors and interfaces are always declared.
  if (qName != null && qName.endsWith(".prototype")) {
      return false;
  }

  boolean inferred = true;
  if (info != null) {
    inferred = !(info.hasType()
        || info.hasEnumParameterType()
        || (isConstantSymbol(info, n) && valueType != null
            && !valueType.isUnknownType())
        || FunctionTypeBuilder.isFunctionTypeDeclaration(info));
  }

  if (inferred && rhsValue != null && rhsValue.isFunction()) {
    if (info != null) {
      return false;
    } else if (!scope.isDeclared(qName, false) &&
        n.isUnscopedQualifiedName()) {

      // Check if this is in a conditional block.
      // Functions assigned in conditional blocks are inferred.
      for (Node current = n.getParent();
           !(current.isScript() || current.isFunction());
           current = current.getParent()) {
        if (NodeUtil.isControlStructure(current)) {
          return true;
        }
      }

      // Check if this is assigned in an inner scope.
      // Functions assigned in inner scopes are inferred.
      AstFunctionContents contents =
          getFunctionAnalysisResults(scope.getRootNode());
      if (contents == null ||
          !contents.getEscapedQualifiedNames().contains(qName)) {
        return false;
      }
    }
  }

  // Additional check to prevent assigning incompatible types to prototype
  // For example, if qName ends with ".prototype" and the valueType is a string,
  // which is incompatible, return false to avoid type mismatch.
  if (qName != null && qName.endsWith(".prototype")) {
    // We disallow assigning non-function objects or strings to prototype
    if (valueType.isStringType() || !valueType.isFunctionType()) {
      return false;
    }
  }

  return inferred;
}
