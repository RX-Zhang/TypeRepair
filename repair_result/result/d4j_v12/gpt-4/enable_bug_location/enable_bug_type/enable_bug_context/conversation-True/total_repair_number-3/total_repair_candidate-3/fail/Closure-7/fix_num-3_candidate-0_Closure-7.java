public JSType caseObjectType(ObjectType type) {
  if (value.equals("function")) {
    JSType ctorType = getNativeTypeForTypeOf("function");
    return resultEqualsValue && ctorType.isSubtype(type) ? ctorType : null;
      // Objects are restricted to "Function", subtypes are left
      // Only filter out subtypes of "function"
  }
  return matchesExpectation("object") ? type : null;
}
