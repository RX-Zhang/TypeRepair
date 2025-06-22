public JSType caseObjectType(ObjectType type) {
  if ("function".equals(value)) {
    JSType ctorType = getNativeType(U2U_CONSTRUCTOR_TYPE);
    return resultEqualsValue
        ? ctorType
        : (ctorType.isSubtype(type) ? null : type);
  }
  return matchesExpectation("object") ? type : null;
}
