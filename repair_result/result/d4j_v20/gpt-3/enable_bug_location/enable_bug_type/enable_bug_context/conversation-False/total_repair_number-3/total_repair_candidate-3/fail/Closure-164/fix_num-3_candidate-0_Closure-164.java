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

    // If this param type is non-null, that param type must be a subtype of this param type
    if (thisParamType != null) {
      if (thatParamType == null || !thatParamType.isSubtype(thisParamType)) {
        return false;
      }
    } else {
      // If this param type is null, that param type must be null as well
      if (thatParamType != null) {
        return false;
      }
    }

    boolean thisIsVarArgs = thisParam.isVarArgs();
    boolean thatIsVarArgs = thatParam.isVarArgs();

    // If this param is required, "that" param must be required
    if (!thatIsVarArgs && thisIsVarArgs) {
      // that param is not var args but this param is var args (this param accepts var args),
      // so that param cannot be required if this is var args
      // This may be acceptable, so no early return here; we rely on type check above
    }

    // Both var_args means end of parameters
    if (thisIsVarArgs && thatIsVarArgs) {
      thisParam = null;
      thatParam = null;
      break;
    }

    if (!thisIsVarArgs) {
      thisParam = thisParam.getNext();
    }
    if (!thatIsVarArgs) {
      thatParam = thatParam.getNext();
    }
  }

  // After matching parameters, check if that has any required parameters left unmatched
  // If "that" still has required parameters, "this" can't be a subtype of "that"
  while (thatParam != null) {
    if (!thatParam.isOptional() && !thatParam.isVarArgs()) {
      // "that" has a required parameter that "this" does not have
      return false;
    }
    thatParam = thatParam.getNext();
  }

  return true;
}
