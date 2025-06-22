public JSType caseObjectType(ObjectType type) {
  if (value.equals("function")) {
    JSType ctorType = getNativeType(U2U_CONSTRUCTOR_TYPE);
    // If we're expecting 'function' and the result matches the expectation,
    // return the constructor type only if 'type' is a subtype of it.
    if (resultEqualsValue) {
      return type.isSubtype(ctorType) ? ctorType : null;
    } else {
      // If the result does not equal 'function', exclude function types
      return type.isSubtype(ctorType) ? null : type;
    }
  }
  return matchesExpectation("object") ? type : null;
}
