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
  
  // Ensure that we traverse the arguments after setting the type
  for (Node arg = constructor.getNext(); arg != null; arg = arg.getNext()) {
    scope = traverse(arg, scope);
  }
  
  // If the constructor type is known, we should also infer the type of 'y'
  if (type != null && type.isObjectType()) {
    // Assuming 'y' is the variable we want to infer the type for
    String yName = "y"; // This should be the name of the variable being constructed
    JSType inferredType = getJSType(n); // Get the inferred type from the node
    if (inferredType != null) {
      // Update the type of 'y' in the scope
      scope.inferQualifiedSlot(constructor.getQualifiedName(), yName, getNativeType(UNKNOWN_TYPE), inferredType);
    }
  }
  
  return scope;
}
