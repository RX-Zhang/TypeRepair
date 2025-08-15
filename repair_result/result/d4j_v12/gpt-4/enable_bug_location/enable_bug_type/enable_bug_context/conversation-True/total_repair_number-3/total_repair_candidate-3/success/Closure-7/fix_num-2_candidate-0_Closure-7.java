public JSType caseObjectType(ObjectType type) {
  if (value.equals("function")) {
    JSType ctorType = getNativeType(U2U_CONSTRUCTOR_TYPE);
    return resultEqualsValue ? (ctorType.isSubtype(type) ? ctorType : null) : type;
  }
  return matchesExpectation("object") ? type : null;
}
