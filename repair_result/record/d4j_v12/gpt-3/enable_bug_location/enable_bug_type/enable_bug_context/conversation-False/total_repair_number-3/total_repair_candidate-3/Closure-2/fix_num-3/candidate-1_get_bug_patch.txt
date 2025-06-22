private void checkInterfaceConflictProperties(NodeTraversal t, Node n,
    String functionName, HashMap<String, ObjectType> properties,
    HashMap<String, ObjectType> currentProperties,
    ObjectType interfaceType) {
  if (interfaceType == null) {
    // Defensive check: if the interfaceType is null (e.g., non-existent),
    // just return to avoid NullPointerException.
    return;
  }
  ObjectType implicitProto = interfaceType.getImplicitPrototype();
  if (implicitProto == null) {
    // Defensive check: if implicit prototype is null, return early.
    return;
  }
  Set<String> currentPropertyNames = implicitProto.getOwnPropertyNames();
  if (currentPropertyNames == null) {
    // Defensive check: if property names set is null, return early.
    return;
  }
  for (String name : currentPropertyNames) {
    ObjectType oType = properties.get(name);
    if (oType != null) {
      if (!interfaceType.getPropertyType(name).isEquivalentTo(
          oType.getPropertyType(name))) {
        compiler.report(
            t.makeError(n, INCOMPATIBLE_EXTENDED_PROPERTY_TYPE,
                functionName, name, oType.toString(),
                interfaceType.toString()));
      }
    }
    currentProperties.put(name, interfaceType);
  }
  for (ObjectType iType : interfaceType.getCtorExtendedInterfaces()) {
    checkInterfaceConflictProperties(t, n, functionName, properties,
        currentProperties, iType);
  }
}
