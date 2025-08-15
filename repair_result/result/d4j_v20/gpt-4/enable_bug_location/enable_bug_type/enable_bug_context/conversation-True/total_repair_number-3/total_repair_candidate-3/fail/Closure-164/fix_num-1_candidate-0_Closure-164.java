public boolean isSubtype(JSType other) {
  if (!(other instanceof ArrowType)) {
    return false;
  }

  ArrowType that = (ArrowType) other;

  // This is described in Draft 2 of the ES4 spec,
  // Section 3.4.7: Subtyping Function Types.

  // that.returnType <: this.returnType (covariant return type)
  if (!that.returnType.isSubtype(this.returnType)) {
    return false;
  }

  // this.paramType[i] <: that.paramType[i] (contravariant parameters)
  //
  // If this.paramType[i] is required,
  // then that.paramType[i] is required.
  //
  // We want to support the use case where a function with fewer parameters
  // can be a subtype of a function with more parameters if the extra 
  // parameters are ignored. Hence, we allow that.paramType list to be 
  // shorter than this.paramType list if that corresponds to optional 
  // parameters or var_args.

  Node thisParam = this.parameters.getFirstChild();
  Node thatParam = that.parameters.getFirstChild();

  while (thisParam != null) {
    JSType thisParamType = thisParam.getJSType();

    if (thatParam == null) {
      // If that is missing parameters that this has and this parameter is required
      // then that cannot be a supertype.
      if (!thisParam.isOptionalArg() && !thisParam.isVarArgs()) {
        return false;
      }
      // Otherwise, treat missing thatParam as unknown which is supertype of thisParamType
      // so continue checking other parameters
    } else {
      JSType thatParamType = thatParam.getJSType();
      if (thisParamType != null) {
        if (thatParamType == null || !thisParamType.isSubtype(thatParamType)) {
          return false;
        }
      }
    }

    boolean thisIsVarArgs = thisParam.isVarArgs();
    boolean thatIsVarArgs = thatParam != null && thatParam.isVarArgs();

    if (thisIsVarArgs && thatIsVarArgs) {
      // Both var_args indicate end of parameters
      break;
    }

    if (!thisIsVarArgs) {
      thisParam = thisParam.getNext();
    } else {
      // If this is var_args, no advance since var_args represents remaining args
      thisParam = null;
    }

    if (thatParam != null && !thatIsVarArgs) {
      thatParam = thatParam.getNext();
    } else {
      thatParam = null;
    }
  }

  // We allow that to have more parameters than this, because extra parameters
  // in a supertype function can be ignored by a subtype function.

  return true;
}
