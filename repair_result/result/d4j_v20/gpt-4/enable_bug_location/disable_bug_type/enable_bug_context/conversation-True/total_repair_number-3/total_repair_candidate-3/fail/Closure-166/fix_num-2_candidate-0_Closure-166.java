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
  // taking each property on the record and trying to match
  // properties on this object.
  if (constraint.isRecordType()) {
    ObjectType constraintObj = constraint.toObjectType();
    for (String prop : constraintObj.getOwnPropertyNames()) {
      JSType propType = constraintObj.getPropertyType(prop);
      if (!this.hasOwnProperty(prop)) {
        defineProperty(prop, propType, true, null);
      }
    }
  }
}
