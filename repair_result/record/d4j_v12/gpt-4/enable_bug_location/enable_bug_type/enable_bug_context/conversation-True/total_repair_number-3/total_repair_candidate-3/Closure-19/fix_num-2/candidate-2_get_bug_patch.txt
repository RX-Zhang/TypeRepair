protected void declareNameInScope(FlowScope scope, Node node, JSType type) {
  JSType refinableType = getTypeIfRefinable(node, scope);
  if (refinableType == null) {
    throw new IllegalArgumentException("Node cannot be refined. \n" + node.toStringTree());
  }
  switch (node.getType()) {
    case Token.NAME:
      scope.inferSlotType(node.getString(), type);
      break;

    case Token.GETPROP:
      String qualifiedName = node.getQualifiedName();
      Preconditions.checkNotNull(qualifiedName);

      scope.inferQualifiedSlot(node, qualifiedName, refinableType, type);
      break;

    default:
      throw new IllegalArgumentException("Node cannot be refined. \n" +
          node.toStringTree());
  }
}
