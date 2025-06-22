private boolean inferTemplatedTypesForCall(
    Node n, FunctionType fnType) {
  final ImmutableList<TemplateType> keys = fnType.getTemplateTypeMap()
      .getTemplateKeys();
  if (keys.isEmpty()) {
    return false;
  }

  // Try to infer the template types
  Map<TemplateType, JSType> inferred = 
      inferTemplateTypesFromParameters(fnType, n);

  // Use the inferred types to update the template map, but preserve any
  // existing mappings by merging inferred with existing template types.
  Map<TemplateType, JSType> updatedInferred = Maps.newIdentityHashMap();
  for (TemplateType key : keys) {
    JSType inferredType = inferred.get(key);
    if (inferredType == null) {
      inferredType = registry.getNativeObjectType(JSTypeNative.UNKNOWN_TYPE);
    }
    updatedInferred.put(key, inferredType);
  }

  // Replace all template types. If we couldn't find a replacement, we
  // replace it with UNKNOWN.
  TemplateTypeReplacer replacer = new TemplateTypeReplacer(
      registry, updatedInferred);
  Node callTarget = n.getFirstChild();

  FunctionType replacementFnType = fnType.visit(replacer)
      .toMaybeFunctionType();
  Preconditions.checkNotNull(replacementFnType);

  callTarget.setJSType(replacementFnType);
  n.setJSType(replacementFnType.getReturnType());

  return replacer.madeChanges;
}
