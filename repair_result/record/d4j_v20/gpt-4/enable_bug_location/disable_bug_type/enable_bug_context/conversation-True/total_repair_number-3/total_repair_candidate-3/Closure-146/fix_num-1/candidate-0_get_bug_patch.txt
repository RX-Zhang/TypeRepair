public TypePair getTypesUnderInequality(JSType that) {
  // unions types
  if (that.isUnionType()) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // restrict types by removing null or undefined
  JSType thisRestricted = this.restrictByNotNullOrUndefined();
  JSType thatRestricted = that.restrictByNotNullOrUndefined();

  // other types
  switch (thisRestricted.testForEquality(thatRestricted)) {
    case TRUE:
      return new TypePair(null, null);

    case FALSE:
    case UNKNOWN:
      return new TypePair(thisRestricted, thatRestricted);
  }

  // switch case is exhaustive
  throw new IllegalStateException();
}
