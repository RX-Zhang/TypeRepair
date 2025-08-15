public final boolean isEmptyType() {
  return this == LEAST_FUNCTION_TYPE || isNoType() || isNoObjectType() || isNoResolvedType();
}
