public boolean isSubtype(JSType other) {
  if (!(other instanceof ArrowType)) {
    return false;
  }

  ArrowType that = (ArrowType) other;

  // This is described in Draft 2 of the ES4 spec,
  // Section 3.4.7: Subtyping Function Types.

  // that.returnType <: this.returnType (covariant)
  // Corrected from original code which reversed covariance
  if (!that.returnType.isSubtype(this.returnType)) {
    return false;
  }

  // that.paramType[i] <: this.paramType[i] (contravariant)
  //
  // If this.paramType[i] is required,
  // then that.paramType[i] is required.
  //
  // If this has more parameters than that, then that cannot be a supertype.
  Node thisParam = parameters.getFirstChild();
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

    // Don't advance if we have var_args
    if (!thisIsVarArgs) {
      thisParam = thisParam.getNext();
    }
    if (!thatIsVarArgs) {
      thatParam = thatParam.getNext();
    }

    // both var_args indicates the end
    if (thisIsVarArgs && thatIsVarArgs) {
      thisParam = null;
      thatParam = null;
    }
  }

  // If this still has parameters but that does not, subtype relation fails
  // unless the remaining parameters in this are optional or varargs
  if (thisParam != null) {
    while (thisParam != null) {
      if (!thisParam.isOptionalArg() && !thisParam.isVarArgs()) {
        return false;
      }
      thisParam = thisParam.getNext();
    }
  }

  return true;
}
