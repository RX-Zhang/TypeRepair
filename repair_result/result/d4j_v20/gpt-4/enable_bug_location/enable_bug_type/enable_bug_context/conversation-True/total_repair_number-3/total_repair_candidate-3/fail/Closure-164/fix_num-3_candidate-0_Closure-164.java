public boolean isSubtype(JSType other) {
  if (!(other instanceof ArrowType)) {
    return false;
  }

  ArrowType that = (ArrowType) other;

  // This is described in Draft 2 of the ES4 spec,
  // Section 3.4.7: Subtyping Function Types.

  // this.returnType <: that.returnType (covariant)
  if (!that.returnType.isSubtype(this.returnType)) {
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
    if (thatParamType != null) {
      if (thisParamType == null ||
          !thisParamType.isSubtype(thatParamType)) {
        return false;
      }
    }

    boolean thisIsVarArgs = thisParam.isVarArgs();
    boolean thatIsVarArgs = thatParam.isVarArgs();

    // "that" can't be a supertype, because it's missing a required argument.
      // NOTE(nicksantos): In our type system, we use {function(...?)} and
      // {function(...NoType)} to to indicate that arity should not be
      // checked. Strictly speaking, this is not a formulation,
      // because now a sub-function can required arguments that are var_args
      // in the super-function. So we special-case this.

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

  // "that" can't be a supertype, because it's missing a required argument.
  if (thisParam != null) {
    // this has more required parameters that that does not have
    // If this parameter is *not* varargs, that means that this function
    // expects more parameters than that, and so is not a subtype.
    // If the parameter is varargs, then that can still be a subtype.
    while (thisParam != null) {
      if (!thisParam.isVarArgs()) {
        return false;
      }
      thisParam = thisParam.getNext();
    }
  }

  if (thatParam != null) {
    // that has more required parameters than this
    // Therefore, this can't be a subtype of that
    return false;
  }

  return true;
}
