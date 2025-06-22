public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that.isUnionType()) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // other types
  switch (this.testForEquality(that)) {
    case TRUE:
      // For TRUE equality, the types restrict to null (no difference)
      return new TypePair(null, null);

    case FALSE:
    case UNKNOWN:
      return new TypePair(this.restrictByNotNullOrUndefined(), that.restrictByNotNullOrUndefined());
  }

  // switch case is exhaustive
  throw new IllegalStateException();
}
