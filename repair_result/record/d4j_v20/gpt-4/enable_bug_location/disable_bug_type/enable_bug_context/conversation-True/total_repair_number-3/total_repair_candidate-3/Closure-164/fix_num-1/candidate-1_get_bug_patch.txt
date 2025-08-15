public boolean isSubtype(JSType other) {
  if (!(other instanceof ArrowType)) {
    return false;
  }

  ArrowType that = (ArrowType) other;

  // This is described in Draft 2 of the ES4 spec,
  // Section 3.4.7: Subtyping Function Types.

  // this.returnType <: that.returnType (covariant)
  if (!this.returnType.isSubtype(that.returnType)) {
    return false;
  }

  // that.paramType[i] <: this.paramType[i] (contravariant)
  //
  // If this.paramType[i] is required,
  // then that.paramType[i] is required.
  //
  // In theory, the "required-ness" should work in the other direction as
  // well. In other words, if we have
  //
  // function f(number, number) {}
  // function g(number) {}
  //
  // Then f *should* not be a subtype of g, and g *should* not be
  // a subtype of f. But in practice, we do not implement it this way.
  // We want to support the use case where you can pass g where f is
  // expected, and pretend that g ignores the second argument.
  // That way, you can have a single "no-op" function, and you don't have
  // to create a new no-op function for every possible type signature.
  //
  // So, in this case, g < f, but f !< g
  Node thisParam = parameters.getFirstChild();
  Node thatParam = that.parameters.getFirstChild();
  while (thisParam != null && thatParam != null) {
    JSType thisParamType = thisParam.getJSType();
    JSType thatParamType = thatParam.getJSType();
    if (thisParamType != null) {
      if (thatParamType == null ||
          !thatParamType.isSubtype(thisParamType)) {
        return false;
      }
    }

    boolean thisIsVarArgs = thisParam.isVarArgs();
    boolean thatIsVarArgs = thatParam.isVarArgs();

    // If "that" is missing a required argument, it can't be a supertype.
    if (!thisIsVarArgs && thatIsVarArgs) {
      // that is varargs but this param is not varargs,
      // so that can accept more arguments, so OK to continue
    } else if (thisIsVarArgs && !thatIsVarArgs) {
      // this is varargs but that is not, so that is missing args
      return false;
    }

    // both var_args indicates the end
    if (thisIsVarArgs && thatIsVarArgs) {
      thisParam = null;
      thatParam = null;
    } else {
      if (!thisIsVarArgs) {
        thisParam = thisParam.getNext();
      }
      if (!thatIsVarArgs) {
        thatParam = thatParam.getNext();
      }
    }
  }

  // If "that" has more required parameters, it can't be a supertype.
  if (thatParam != null) {
    // Check if the remaining parameters of "that" are optional or varargs
    while (thatParam != null) {
      if (!thatParam.isOptionalArg() && !thatParam.isVarArgs()) {
        return false;
      }
      thatParam = thatParam.getNext();
    }
  }

  return true;
}
