public boolean isSubtype(JSType other) {
  if (!(other instanceof ArrowType)) {
    return false;
  }

  ArrowType that = (ArrowType) other;

  // If either has unknown parameters or return, treat conservatively.
  if (this.hasUnknownParamsOrReturn() || that.hasUnknownParamsOrReturn()) {
    return true;
  }

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

    // don't advance if we have variable arguments
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

  // If "that" function has more required parameters than "this",
  // then "that" can't be a supertype, because it's missing a required argument.
  if (thatParam != null && !thatParam.isOptionalArg()) {
    return false;
  }

  return true;
}
