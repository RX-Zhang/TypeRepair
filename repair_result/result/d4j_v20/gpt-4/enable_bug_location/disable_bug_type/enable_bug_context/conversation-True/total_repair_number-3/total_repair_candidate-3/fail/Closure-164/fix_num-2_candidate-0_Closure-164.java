public boolean isSubtype(JSType other) {
  if (!(other instanceof ArrowType)) {
    return false;
  }

  ArrowType that = (ArrowType) other;

  // This is described in Draft 2 of the ES4 spec,
  // Section 3.4.7: Subtyping Function Types.

  // Covariant return type: this.returnType <: that.returnType
  if (!that.returnType.isSubtype(this.returnType)) {
    return false;
  }

  // Contravariant parameter types:
  // that.paramType[i] <: this.paramType[i]
  //
  // Check parameters contravariantly:
  Node thisParam = this.parameters.getFirstChild();
  Node thatParam = that.parameters.getFirstChild();

  while (thisParam != null && thatParam != null) {
    JSType thisParamType = thisParam.getJSType();
    JSType thatParamType = thatParam.getJSType();

    if (thisParamType != null) {
      if (thatParamType == null || !thisParamType.isSubtype(thatParamType)) {
        return false;
      }
    }

    boolean thisIsVarArgs = thisParam.isVarArgs();
    boolean thatIsVarArgs = thatParam.isVarArgs();

    // If "that" does not have a required parameter that "this" requires, fail
    if (thisIsVarArgs && !thatIsVarArgs) {
      return false;
    }

    // Advance nodes unless var args argument is present, in which case don't advance
    if (!thisIsVarArgs) {
      thisParam = thisParam.getNext();
    }
    if (!thatIsVarArgs) {
      thatParam = thatParam.getNext();
    }

    if (thisIsVarArgs && thatIsVarArgs) {
      // Both have var args at this position means parameter checking ends
      thisParam = null;
      thatParam = null;
    }
  }

  // "that" cannot be a supertype if it has fewer parameters than "this" (other parameters remain)
  // We already handled varargs above, so if thisParam != null then "this" has required parameters missing in "that"
  if (thisParam != null) {
    return false;
  }

  return true;
}
