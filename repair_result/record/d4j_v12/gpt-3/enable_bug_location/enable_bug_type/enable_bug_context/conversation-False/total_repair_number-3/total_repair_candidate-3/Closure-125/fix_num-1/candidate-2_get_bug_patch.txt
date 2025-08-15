private void visitNew(NodeTraversal t, Node n) {
  Node constructor = n.getFirstChild();
  JSType type = getJSType(constructor).restrictByNotNullOrUndefined();
  // Defensive check: if type is null, unknown, or empty, try to handle gracefully.
  if (type.isEmptyType() || type.isUnknownType()) {
    ensureTyped(t, n);
    return;
  }
  if (type.isConstructor()) {
    FunctionType fnType = type.toMaybeFunctionType();
    if (fnType != null) {
      visitParameterList(t, n, fnType);
      ensureTyped(t, n, fnType.getInstanceType());
    } else {
      // Unexpected case: type is constructor but no function type
      ensureTyped(t, n);
    }
  } else {
    report(t, n, NOT_A_CONSTRUCTOR);
    ensureTyped(t, n);
  }
}
