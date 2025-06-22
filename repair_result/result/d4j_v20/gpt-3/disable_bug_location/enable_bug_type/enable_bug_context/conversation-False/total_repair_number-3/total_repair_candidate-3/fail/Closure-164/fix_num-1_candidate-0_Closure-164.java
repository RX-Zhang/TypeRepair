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
    // If thisParam is null but thatParam is not, then that has more parameters.
    // So this is missing required parameter(s), which means this can't be a subtype.
    if (thisParam == null) {
      // If thatParam is varargs or optional parameters it might be okay,
      // but the existing code doesn't handle that scenario, so return false.
      return false;
    }

    JSType thisParamType = thisParam.getJSType();
    JSType thatParamType = (thatParam == null) ? null : thatParam.getJSType();

    if (thisParamType != null) {
      if (thatParamType == null || !thatParamType.isSubtype(thisParamType)) {
        return false;
      }
    } else {
      // If thisParamType is null, but thatParamType is not, they don't match.
      if (thatParamType != null) {
        return false;
      }
    }

    boolean thisIsVarArgs = thisParam.isVarArgs();
    boolean thatIsVarArgs = (thatParam != null) && thatParam.isVarArgs();

    // If one is varargs but the other is not, then the other must be missing required args.
    if (thisIsVarArgs && !thatIsVarArgs) {
      // this expects varargs, that doesn't, so that is missing required args
      return false;
    }
    if (!thisIsVarArgs && thatIsVarArgs) {
      // that expects varargs, this doesn't, so this might be missing required args
      // but the contravariance requires that that.paramType <: this.paramType, so this param must be subtype of that param
      // since this param is not varargs and that is varargs, this param should exist, so continue
    }

    // Advance the parameters, unless varargs are involved.
    if (!thisIsVarArgs) {
      thisParam = thisParam.getNext();
    }
    if (!thatIsVarArgs) {
      thatParam = (thatParam == null) ? null : thatParam.getNext();
    }

    // If both are varargs, end the loop.
    if (thisIsVarArgs && thatIsVarArgs) {
      break;
    }
  }

  return true;
}
