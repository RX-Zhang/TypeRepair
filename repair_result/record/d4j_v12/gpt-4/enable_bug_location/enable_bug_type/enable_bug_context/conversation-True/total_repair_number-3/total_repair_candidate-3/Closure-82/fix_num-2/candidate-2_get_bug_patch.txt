public final boolean isEmptyType() {
  return this == JSTypeRegistry.getNoType() || this == JSTypeRegistry.getNoObjectType() || this == JSTypeRegistry.getNoResolvedType();
}
