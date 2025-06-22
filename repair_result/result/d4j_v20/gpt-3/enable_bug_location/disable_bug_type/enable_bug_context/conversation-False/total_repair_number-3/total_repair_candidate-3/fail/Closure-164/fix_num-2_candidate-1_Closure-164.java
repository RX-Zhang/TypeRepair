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

  while (thisParam != null || thatParam != null) {
    JSType thisParamType = thisParam != null ? thisParam.getJSType() : null;
    JSType thatParamType = thatParam != null ? thatParam.getJSType() : null;

    if (thisParamType != null) {
      if (thatParamType == null || !thatParamType.isSubtype(thisParamType)) {
        return false;
      }
    } else {
      // If this param type is null but that param type is non-null, fail
      if (thatParamType != null) {
        return false;
      }
    }

    boolean thisIsVarArgs = thisParam != null && thisParam.isVarArgs();
    boolean thatIsVarArgs = thatParam != null && thatParam.isVarArgs();

    // If both are var_args, we can consider there are no more parameters
    if (thisIsVarArgs && thatIsVarArgs) {
      break;
    }

    // If "that" function has var_args param but "this" doesn't, "that" is a supertype only if
    // "that" param is subtype of "this" param and "this" has no more parameters
    if (thatIsVarArgs && !thisIsVarArgs) {
      // "thisParam" is non-varargs, "thatParam" is varargs
      // Move 'thatParam' forward only if it's not var_args (if var_args, it covers unlimited params)
      thatParam = thatParam.getNext();
      // But if "thisParam" has more params, "that" is missing required args
      if (thisParam != null && thisParam.getNext() != null) {
        return false;
      }
      break;
    }

    // If "this" function has var_args param but "that" doesn't, then "that" cannot be a supertype
    if (thisIsVarArgs && !thatIsVarArgs) {
      return false;
    }

    // Advance to next parameter if not var_args
    if (!thisIsVarArgs) {
      thisParam = (thisParam != null) ? thisParam.getNext() : null;
    }
    if (!thatIsVarArgs) {
      thatParam = (thatParam != null) ? thatParam.getNext() : null;
    }
  }

  return true;
}
