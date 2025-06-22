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
    // We cannot cast safely, so set typeOfThis to null or a default ObjectType.
    // Or keep the original typeOfThis, depending on upstream expectations.
    typeOfThis = null;
  }

  boolean changed = false;
  ImmutableList.Builder<ObjectType> resolvedInterfaces =
      ImmutableList.builder();
  for (ObjectType iface : implementedInterfaces) {
    JSType resolved = iface.resolve(t, scope);
    if (resolved instanceof ObjectType) {
      ObjectType resolvedIface = (ObjectType) resolved;
      resolvedInterfaces.add(resolvedIface);
      changed |= (resolvedIface != iface);
    } else {
      // If resolved iface is not an ObjectType, skip or handle appropriately
      // Here, we skip adding to the list to avoid type errors
      changed = true;
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
        // If resolvedSubType is not a FunctionType, set to null or handle appropriately
        subTypes.set(i, null);
      }
    }
  }

  return super.resolveInternal(t, scope);
}
