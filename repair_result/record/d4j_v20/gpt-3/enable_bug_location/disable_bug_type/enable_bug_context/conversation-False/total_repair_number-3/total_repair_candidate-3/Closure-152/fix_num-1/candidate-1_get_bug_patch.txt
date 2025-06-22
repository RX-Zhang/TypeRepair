JSType resolveInternal(ErrorReporter t, StaticScope<JSType> scope) {
  setResolvedTypeInternal(this);

  call = (ArrowType) safeResolve(call, t, scope);
  prototype = (FunctionPrototypeType) safeResolve(prototype, t, scope);

  // Warning about typeOfThis if it doesn't resolve to an ObjectType
  // is handled further upstream.
  // TODO(nicksantos): Handle this correctly if we have a UnionType.
  JSType resolvedTypeOfThis = safeResolve(typeOfThis, t, scope);
  if (resolvedTypeOfThis instanceof ObjectType) {
    typeOfThis = (ObjectType) resolvedTypeOfThis;
  } else {
    typeOfThis = null; // or alternatively: typeOfThis = registry.getNativeObjectType(JSTypeNative.NO_OBJECT_TYPE);
  }

  boolean changed = false;
  ImmutableList.Builder<ObjectType> resolvedInterfaces =
      ImmutableList.builder();
  for (ObjectType iface : implementedInterfaces) {
    JSType resolved = iface.resolve(t, scope);
    if (!(resolved instanceof ObjectType)) {
      // If resolved iface is not ObjectType, skip or handle accordingly.
      continue;
    }
    ObjectType resolvedIface = (ObjectType) resolved;
    resolvedInterfaces.add(resolvedIface);
    changed |= (resolvedIface != iface);
  }
  if (changed) {
    implementedInterfaces = resolvedInterfaces.build();
  }

  if (subTypes != null) {
    for (int i = 0; i < subTypes.size(); i++) {
      subTypes.set(i, (FunctionType) subTypes.get(i).resolve(t, scope));
    }
  }

  return super.resolveInternal(t, scope);
}
