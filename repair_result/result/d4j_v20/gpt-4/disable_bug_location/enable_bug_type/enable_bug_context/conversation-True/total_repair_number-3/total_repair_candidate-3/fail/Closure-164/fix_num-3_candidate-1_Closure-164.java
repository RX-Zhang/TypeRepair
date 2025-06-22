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
  // For each parameter, that.paramType[i] <: this.paramType[i]
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

    // If "that" is missing a required parameter that "this" expects,
    // then it's not a supertype.
    if (!thisIsVarArgs && thatIsVarArgs) {
      // "that" has var args but "this" does not: ok, accept as supertype
      // continue normally
    } else if (thisIsVarArgs && !thatIsVarArgs) {
      // "this" has var args but "that" does not: "that" is missing args
      return false;
    }

    if (!thisIsVarArgs) {
      thisParam = thisParam.getNext();
    }
    if (!thatIsVarArgs) {
      thatParam = thatParam.getNext();
    }

    if (thisIsVarArgs && thatIsVarArgs) {
      // Both var args means end of parameters
      thisParam = null;
      thatParam = null;
    }
  }

  // If this still has parameters while that does not, then that is missing required args
  if (thisParam != null) {
    // If leftover params in this are all optional/varargs, it's okay
    // Here, conservatively, return false as that is missing required args
    return false;
  }

  return true;
}
