JSType resolveInternal(ErrorReporter t, StaticScope<JSType> scope) {
  setResolvedTypeInternal(this);

  call = (ArrowType) safeResolve(call, t, scope);
  prototype = (FunctionPrototypeType) safeResolve(prototype, t, scope);

  // Safely resolve typeOfThis and convert to ObjectType if possible to avoid ClassCastException
  JSType resolvedTypeOfThis = safeResolve(typeOfThis, t, scope);
  if (resolvedTypeOfThis.isObject() || resolvedTypeOfThis.isUnknownType()) {
    typeOfThis = (ObjectType) resolvedTypeOfThis;
  } else {
    // Handle the case where typeOfThis is not an ObjectType:
    // Use getTypeOfThis() method which handles NoObjectType internally.
    typeOfThis = getTypeOfThis();
  }

  boolean changed = false;
  ImmutableList.Builder<ObjectType> resolvedInterfaces = ImmutableList.builder();
  for (ObjectType iface : implementedInterfaces) {
    ObjectType resolvedIface = (ObjectType) iface.resolve(t, scope);
    resolvedInterfaces.add(resolvedIface);
    changed |= (resolvedIface != iface);
  }
  if (changed) {
    setImplementedInterfaces(resolvedInterfaces.build());
  }

  if (subTypes != null) {
    for (int i = 0; i < subTypes.size(); i++) {
      subTypes.set(i, (FunctionType) subTypes.get(i).resolve(t, scope));
    }
  }

  return super.resolveInternal(t, scope);
}
