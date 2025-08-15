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
      // If this param type is null but that param type isn't, fail.
      if (thatParamType != null) {
        return false;
      }
    }

    boolean thisIsVarArgs = thisParam != null && thisParam.isVarArgs();
    boolean thatIsVarArgs = thatParam != null && thatParam.isVarArgs();

    // both var_args indicates the end
    if (thisIsVarArgs && thatIsVarArgs) {
      break;
    }

    // don't advance if we have variable arguments
    if (thisParam != null && !thisIsVarArgs) {
      thisParam = thisParam.getNext();
    } else if (thisIsVarArgs) {
      thisParam = null;
    }

    if (thatParam != null && !thatIsVarArgs) {
      thatParam = thatParam.getNext();
    } else if (thatIsVarArgs) {
      thatParam = null;
    }
  }

  // No missing required arguments in 'that' compared to 'this' detected,
  // so 'this' can be a subtype of 'that'.
  return true;
}
