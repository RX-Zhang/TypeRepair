public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that.isUnionType()) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  if (this.isNoType() || that.isNoType()) {
    return new TypePair(this.restrictByNotNullOrUndefined(), that.restrictByNotNullOrUndefined());
  }

  if (this.isUnknownType() || that.isUnknownType()) {
    return new TypePair(null, null);
  }

  // other types
  switch (this.testForEquality(that)) {
    case TRUE:
      return new TypePair(null, null);

    case FALSE:
    case UNKNOWN:
      return new TypePair(this, that);
  }

  // switch case is exhaustive
  throw new IllegalStateException();
}
