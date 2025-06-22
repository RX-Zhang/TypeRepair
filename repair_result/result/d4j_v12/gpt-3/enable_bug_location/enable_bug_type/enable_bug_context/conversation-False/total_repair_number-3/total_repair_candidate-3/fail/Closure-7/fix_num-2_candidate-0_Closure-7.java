public JSType caseObjectType(ObjectType type) {
  if (value.equals("function")) {
    JSType ctorType = getNativeType(U2U_CONSTRUCTOR_TYPE);
    // Return ctorType if resultEqualsValue is true and type is a subtype of ctorType OR
    // if resultEqualsValue is false and type is NOT a subtype of ctorType.
    if (resultEqualsValue) {
      return ctorType.isSubtype(type) ? ctorType : null;
    } else {
      return ctorType.isSubtype(type) ? null : type;
    }
  }
  return matchesExpectation("object") == resultEqualsValue ? type : null;
}
