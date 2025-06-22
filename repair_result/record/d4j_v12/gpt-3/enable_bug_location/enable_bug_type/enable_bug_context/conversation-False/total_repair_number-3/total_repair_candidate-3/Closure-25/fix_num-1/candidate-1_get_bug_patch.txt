private FlowScope traverseNew(Node n, FlowScope scope) {

  Node constructor = n.getFirstChild();
  scope = traverse(constructor, scope);
  JSType constructorType = constructor.getJSType();
  JSType type = null;
  if (constructorType != null) {
    constructorType = constructorType.restrictByNotNullOrUndefined();
    if (constructorType.isUnknownType()) {
      type = getNativeType(UNKNOWN_TYPE);
    } else {
      FunctionType ct = constructorType.toMaybeFunctionType();
      if (ct == null && constructorType instanceof FunctionType) {
        // If constructorType is a NoObjectType, then toMaybeFunctionType will
        // return null. But NoObjectType implements the FunctionType
        // interface, precisely because it can validly construct objects.
        ct = (FunctionType) constructorType;
      }
      if (ct != null && ct.isConstructor()) {
        type = ct.getInstanceType();
      }
    }
  }
  n.setJSType(type);
  
  // Ensure we are checking the argument types correctly
  for (Node arg = constructor.getNext(); arg != null; arg = arg.getNext()) {
    JSType argType = getJSType(arg);
    if (argType == null || argType.isUnknownType()) {
      // If the argument type is unknown, we should handle it appropriately
      compiler.report(JSError.make(NodeUtil.getSourceName(arg), arg,
          TEMPLATE_TYPE_NOT_OBJECT_TYPE, "Argument type is unknown."));
    }
    scope = traverse(arg, scope);
  }
  return scope;
}
