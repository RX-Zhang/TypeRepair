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
    // If it's not an ObjectType, retain the original or process accordingly.
    typeOfThis = null; // or keep previous or handle as needed
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
      // If not ObjectType after resolve, add original iface or handle accordingly
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
      }
      // else keep original or handle error as needed
    }
  }

  return super.resolveInternal(t, scope);
}
