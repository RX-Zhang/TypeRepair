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
    typeOfThis = null; // or set to a default ObjectType if appropriate
  }

  boolean changed = false;
  ImmutableList.Builder<ObjectType> resolvedInterfaces =
      ImmutableList.builder();
  for (ObjectType iface : implementedInterfaces) {
    JSType resolvedIfaceType = iface.resolve(t, scope);
    if (resolvedIfaceType instanceof ObjectType) {
      ObjectType resolvedIface = (ObjectType) resolvedIfaceType;
      resolvedInterfaces.add(resolvedIface);
      changed |= (resolvedIface != iface);
    } else {
      // handle unexpected type: skip or add null? Adding null is dangerous, so skip
      // Or optionally add iface as fallback
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
        // fallback: keep original if unable to cast
        // or possibly remove or handle differently
        subTypes.set(i, subTypes.get(i));
      }
    }
  }

  return super.resolveInternal(t, scope);
}
