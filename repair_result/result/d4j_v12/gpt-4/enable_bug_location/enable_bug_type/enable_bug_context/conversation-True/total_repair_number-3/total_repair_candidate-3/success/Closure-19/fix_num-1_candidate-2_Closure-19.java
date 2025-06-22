protected void declareNameInScope(FlowScope scope, Node node, JSType type) {
  switch (node.getType()) {
    case Token.NAME:
      scope.inferSlotType(node.getString(), type);
      break;

    case Token.GETPROP:
      String qualifiedName = node.getQualifiedName();
      Preconditions.checkNotNull(qualifiedName);

      JSType origType = node.getJSType();
      origType = origType == null ? getNativeType(UNKNOWN_TYPE) : origType;
      scope.inferQualifiedSlot(node, qualifiedName, origType, type);
      break;

    case Token.THIS:
      // "this" references are currently modeled by delegating to the next link
      FlowScope refinedScope = firstPreciserScopeKnowingConditionOutcome(node, scope, true);
      if (refinedScope != null) {
        return;  // The scope refinement handled by the chain, no need to throw
      }
      throw new IllegalArgumentException("Node cannot be refined. \n" +
          node.toStringTree());

    default:
      throw new IllegalArgumentException("Node cannot be refined. \n" +
          node.toStringTree());
  }
}
