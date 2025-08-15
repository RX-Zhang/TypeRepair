private void inferPropertyTypesToMatchConstraint(
    JSType type, JSType constraint) {
  if (type == null || constraint == null) {
    return;
  }

  ObjectType constraintObj =
      ObjectType.cast(constraint.restrictByNotNullOrUndefined());
  if (constraintObj != null && constraintObj.isRecordType()) {
    ObjectType objType = ObjectType.cast(type.restrictByNotNullOrUndefined());
    if (objType != null) {
      for (String prop : constraintObj.getOwnPropertyNames()) {
        JSType propType = constraintObj.getPropertyType(prop);
        if (!objType.isPropertyTypeDeclared(prop)) {
          JSType typeToInfer = propType;
          if (!objType.hasProperty(prop)) {
            typeToInfer =
                getNativeType(VOID_TYPE).getLeastSupertype(propType);
          }
          objType.defineInferredProperty(prop, typeToInfer, null);
        } else {
          // Ensure we handle the case where the property is already declared
          JSType existingType = objType.getPropertyType(prop);
          if (existingType != null) {
            // Infer the least supertype if there's a type conflict
            JSType leastSupertype = existingType.getLeastSupertype(propType);
            objType.defineInferredProperty(prop, leastSupertype, null);
          }
        }
      }
    }
  }
}
