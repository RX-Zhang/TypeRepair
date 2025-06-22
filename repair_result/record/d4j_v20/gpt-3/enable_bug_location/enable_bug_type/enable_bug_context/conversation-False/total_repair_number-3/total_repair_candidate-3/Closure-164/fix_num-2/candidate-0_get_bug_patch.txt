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
    if (thisParam == null) {
      // This means that "this" function has fewer params than "that".
      // Check if "thatParam" are optional or var args or both.
      // If "that" has any required params beyond this end, "this" is not a subtype.
      // But this code doesn't have access to that info, so conservatively return false.
      // However, because in practice we accept fewer params, only fail if "that" param is required
      // and "this" param is missing.
      if (thatParam != null && !thatParam.isOptionalArg() && !thatParam.isVarArgs()) {
        return false;
      }
      thatParam = thatParam.getNext();
      continue;
    }
    if (thatParam == null) {
      // "that" has fewer params, so "this" must have all params optional or var args
      // to be a subtype.
      if (!thisParam.isOptionalArg() && !thisParam.isVarArgs()) {
        return false;
      }
      thisParam = thisParam.getNext();
      continue;
    }

    JSType thisParamType = thisParam.getJSType();
    JSType thatParamType = thatParam.getJSType();

    if (thisParamType != null) {
      if (thatParamType == null || !thatParamType.isSubtype(thisParamType)) {
        return false;
      }
    }

    boolean thisIsVarArgs = thisParam.isVarArgs();
    boolean thatIsVarArgs = thatParam.isVarArgs();

    // If "that" param is required, then "this" param should also be required or varargs.
    // But we allow "this" param to be varargs to accept "that" param required.
    // This is consistent with the comment above.
    if (!thisIsVarArgs && thatIsVarArgs) {
      // "that" has varargs while "this" does not; that means "this" is missing potentially additional args.
      return false;
    }

    // Advance parameters appropriately.
    if (!thisIsVarArgs) {
      thisParam = thisParam.getNext();
    }
    if (!thatIsVarArgs) {
      thatParam = thatParam.getNext();
    }

    if (thisIsVarArgs && thatIsVarArgs) {
      // both var_args indicates the end
      thisParam = null;
      thatParam = null;
    }
  }

  return true;
}
