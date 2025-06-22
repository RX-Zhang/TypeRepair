public void matchConstraint(JSType constraint) {
  // We only want to match constraints on anonymous types.
  if (hasReferenceName()) {
    return;
  }

  // Handle the case where the constraint object is a record type.
  //
  // param constraint {{prop: (number|undefined)}}
  // function f(constraint) {}
  // f({});
  //
  // We want to modify the object literal to match the constraint, by
  // taking any each property on the record and trying to match
  // properties on this object.
  if (constraint.isRecordType()) {
    ObjectType constraintObj = constraint.toObjectType();

    for (String propName : constraintObj.getOwnPropertyNames()) {
      JSType constraintPropType = constraintObj.getPropertyType(propName);

      if (hasOwnProperty(propName)) {
        // Property exists, so try to match types accordingly.
        JSType existingType = getPropertyType(propName);
        if (!existingType.equals(constraintPropType)) {
          defineProperty(propName, constraintPropType, true /* inferred */, null);
        }
      } else {
        // Property does not exist, define it using the constraint type.
        defineProperty(propName, constraintPropType, true /* inferred */, null);
      }
    }
  }
}
