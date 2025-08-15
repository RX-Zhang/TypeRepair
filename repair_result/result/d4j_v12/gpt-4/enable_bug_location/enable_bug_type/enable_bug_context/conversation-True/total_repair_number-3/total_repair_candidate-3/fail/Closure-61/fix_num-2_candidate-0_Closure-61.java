static boolean functionCallHasSideEffects(
    Node callNode, @Nullable AbstractCompiler compiler) {
  if (callNode.getType() != Token.CALL) {
    throw new IllegalStateException(
        "Expected CALL node, got " + Token.name(callNode.getType()));
  }

  if (callNode.isNoSideEffectsCall()) {
    return false;
  }

  Node nameNode = callNode.getFirstChild();

  // Built-in functions with no side effects.
  if (nameNode.getType() == Token.NAME) {
    String name = nameNode.getString();
    if (BUILTIN_FUNCTIONS_WITHOUT_SIDEEFFECTS.contains(name)) {
      return false;
    }
  } else if (nameNode.getType() == Token.GETPROP) {
    if (OBJECT_METHODS_WITHOUT_SIDEEFFECTS.contains(
          nameNode.getLastChild().getString())) {
      return false;
    }

    if (callNode.isOnlyModifiesThisCall()
        && evaluatesToLocalValue(nameNode.getFirstChild())) {
      return false;
    }

    // Functions in the "Math" namespace have no side effects.
    Node first = nameNode.getFirstChild();
    String lastProp = nameNode.getLastChild().getString();
    if (first != null && first.getType() == Token.NAME && "Math".equals(first.getString())) {
      return false;
    }

    if (compiler != null && !compiler.hasRegExpGlobalReferences()) {
      if (first != null && first.getType() == Token.REGEXP
          && REGEXP_METHODS.contains(lastProp)) {
        return false;
      } else if (first != null && first.getType() == Token.STRING
          && STRING_REGEXP_METHODS.contains(lastProp)) {
        Node param = nameNode.getNext();
        if (param != null &&
            (param.getType() == Token.STRING
                || param.getType() == Token.REGEXP))
          return false;
      }
    }
  }

  return true;
}
