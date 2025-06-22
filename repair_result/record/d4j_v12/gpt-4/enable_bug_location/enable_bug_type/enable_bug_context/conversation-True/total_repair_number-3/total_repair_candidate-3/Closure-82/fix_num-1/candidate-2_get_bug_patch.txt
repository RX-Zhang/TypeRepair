public final boolean isEmptyType() {
  return isNoType() || isNoObjectType() || isNoResolvedType() || (isResolved() && findPropertyType("") == null);
}
