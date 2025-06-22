protected void declareNameInScope(FlowScope scope, Node node, JSType type) {
  JSType origType = getTypeIfRefinable(node, scope);
  if (origType == null) {
    origType = getNativeType(UNKNOWN_TYPE);
  }
  switch (node.getType()) {
    case Token.NAME:
      scope.inferSlotType(node.getString(), type);
      break;

    case Token.GETPROP:
      String qualifiedName = node.getQualifiedName();
      Preconditions.checkNotNull(qualifiedName);
      scope.inferQualifiedSlot(node, qualifiedName, origType, type);
      break;

      // "this" references aren't currently modeled in the CFG.

    default:
      throw new IllegalArgumentException("Node cannot be refined. \n" +
          node.toStringTree());
  }
}
