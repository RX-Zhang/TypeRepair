private void checkInterfaceConflictProperties(NodeTraversal t, Node n,
    String functionName, HashMap<String, ObjectType> properties,
    HashMap<String, ObjectType> currentProperties,
    ObjectType interfaceType) {
  if (interfaceType == null) {
    // Defensive check: if interfaceType is null, nothing to check.
    return;
  }

  ObjectType implicitProto = interfaceType.getImplicitPrototype();
  if (implicitProto == null) {
    // Defensive check: if implicitProto is null, skip property checks.
    return;
  }

  Set<String> currentPropertyNames = implicitProto.getOwnPropertyNames();
  if (currentPropertyNames == null) {
    // Defensive check: if property names set is null, skip further processing.
    return;
  }

  for (String name : currentPropertyNames) {
    ObjectType oType = properties.get(name);
    if (oType != null) {
      // Defensive check: ensure property types are not null before comparing
      JSType interfacePropType = interfaceType.getPropertyType(name);
      JSType oTypePropType = oType.getPropertyType(name);
      if (interfacePropType == null || oTypePropType == null) {
        // If property types cannot be resolved, report unresolved type error
        report(t, n, INCOMPATIBLE_EXTENDED_PROPERTY_TYPE,
            functionName, name,
            String.valueOf(oTypePropType),
            String.valueOf(interfacePropType));
        continue;
      }
      if (!interfacePropType.isEquivalentTo(oTypePropType)) {
        report(t, n, INCOMPATIBLE_EXTENDED_PROPERTY_TYPE,
            functionName, name, oType.toString(),
            interfaceType.toString());
      }
    }
    currentProperties.put(name, interfaceType);
  }

  if (interfaceType.getCtorExtendedInterfaces() != null) {
    for (ObjectType iType : interfaceType.getCtorExtendedInterfaces()) {
      if (iType != null) {
        checkInterfaceConflictProperties(t, n, functionName, properties,
            currentProperties, iType);
      }
    }
  }
}
