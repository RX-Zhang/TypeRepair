private void checkInterfaceConflictProperties(NodeTraversal t, Node n,
    String functionName, HashMap<String, ObjectType> properties,
    HashMap<String, ObjectType> currentProperties,
    ObjectType interfaceType) {
  if (interfaceType == null) {
    // Defensive check for null interfaceType to avoid NullPointerException
    return;
  }
  ObjectType implicitProto = interfaceType.getImplicitPrototype();
  if (implicitProto == null) {
    // Defensive check for null implicitProto to avoid NullPointerException
    return;
  }
  Set<String> currentPropertyNames = implicitProto.getOwnPropertyNames();
  if (currentPropertyNames == null) {
    // Defensive check if getOwnPropertyNames returns null
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
