public JSType caseObjectType(ObjectType type) {
  if (value.equals("function")) {
    JSType ctorType = getNativeType(U2U_CONSTRUCTOR_TYPE);
    if (resultEqualsValue) {
      return ctorType.isSubtype(type) ? ctorType : null;
    } else {
      // If resultEqualsValue is false, exclude subtypes of function
      return null;
    }
  }
  return matchesExpectation("object") ? type : null;
}
