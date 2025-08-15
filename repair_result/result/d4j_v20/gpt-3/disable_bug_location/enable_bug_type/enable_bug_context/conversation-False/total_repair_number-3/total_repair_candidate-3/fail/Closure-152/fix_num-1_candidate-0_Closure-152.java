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
    // Keep typeOfThis unchanged or set to null if it cannot be cast safely
    typeOfThis = null;
  }

  boolean changed = false;
  ImmutableList.Builder<ObjectType> resolvedInterfaces =
      ImmutableList.builder();
  for (ObjectType iface : implementedInterfaces) {
    JSType resolvedIfaceRaw = iface.resolve(t, scope);
    if (resolvedIfaceRaw instanceof ObjectType) {
      ObjectType resolvedIface = (ObjectType) resolvedIfaceRaw;
      resolvedInterfaces.add(resolvedIface);
      changed |= (resolvedIface != iface);
    } else {
      // If resolved iface is not an ObjectType, add original iface
      resolvedInterfaces.add(iface);
    }
  }
  if (changed) {
    implementedInterfaces = resolvedInterfaces.build();
  }

  if (subTypes != null) {
    for (int i = 0; i < subTypes.size(); i++) {
      JSType resolvedSubtypeRaw = subTypes.get(i).resolve(t, scope);
      if (resolvedSubtypeRaw instanceof FunctionType) {
        subTypes.set(i, (FunctionType) resolvedSubtypeRaw);
      }
      // else: keep the original subtype
    }
  }

  return super.resolveInternal(t, scope);
}
