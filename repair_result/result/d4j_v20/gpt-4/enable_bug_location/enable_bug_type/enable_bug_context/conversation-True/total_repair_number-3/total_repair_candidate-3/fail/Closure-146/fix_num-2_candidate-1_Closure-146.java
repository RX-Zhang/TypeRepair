public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that.isUnionType()) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // other types
  switch (this.testForEquality(that)) {
    case TRUE:
      // If the two types are equal, no types remain after inequality test
      return new TypePair(null, null);

    case FALSE:
    case UNKNOWN:
      // For inequality, remove types that are 'no type' or 'void' from results
      JSType resultThis = this.isNoType() || this.isVoidType() ? null : this;
      JSType resultThat = that.isNoType() || that.isVoidType() ? null : that;
      return new TypePair(resultThis, resultThat);
  }

  // switch case is exhaustive
  throw new IllegalStateException();
}
