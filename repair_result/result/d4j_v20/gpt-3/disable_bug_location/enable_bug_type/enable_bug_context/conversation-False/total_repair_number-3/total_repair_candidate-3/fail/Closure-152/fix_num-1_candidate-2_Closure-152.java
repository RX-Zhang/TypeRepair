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
    // Keep the resolved JSType as is if not ObjectType to avoid ClassCastException.
    // This allows handling non-ObjectType types safely upstream.
    typeOfThis = null;
  }

  boolean changed = false;
  ImmutableList.Builder<ObjectType> resolvedInterfaces =
      ImmutableList.builder();
  for (ObjectType iface : implementedInterfaces) {
    JSType resolvedIfaceJSType = iface.resolve(t, scope);
    if (resolvedIfaceJSType instanceof ObjectType) {
      ObjectType resolvedIface = (ObjectType) resolvedIfaceJSType;
      resolvedInterfaces.add(resolvedIface);
      changed |= (resolvedIface != iface);
    } else {
      // If resolvedIfaceJSType is not ObjectType, add iface itself to maintain list integrity
      resolvedInterfaces.add(iface);
    }
  }
  if (changed) {
    implementedInterfaces = resolvedInterfaces.build();
  }

  if (subTypes != null) {
    for (int i = 0; i < subTypes.size(); i++) {
      JSType resolvedSubType = subTypes.get(i).resolve(t, scope);
      if (resolvedSubType instanceof FunctionType) {
        subTypes.set(i, (FunctionType) resolvedSubType);
      } else {
        // If not a FunctionType, keep the old one (or handle accordingly)
        // Here, we keep the old subtype to prevent ClassCastException.
      }
    }
  }

  return super.resolveInternal(t, scope);
}
