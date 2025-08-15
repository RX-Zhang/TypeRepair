public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that.isUnionType()) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  JSType thisRestricted = this.restrictByNotNullOrUndefined();
  JSType thatRestricted = that.restrictByNotNullOrUndefined();

  // If either type is UNKNOWN or NO_TYPE, treat appropriately
  if (thisRestricted.isUnknownType() || thatRestricted.isUnknownType()) {
    return new TypePair(null, null);
  }
  if (thisRestricted.isNoType() || thatRestricted.isNoType()) {
    return new TypePair(thisRestricted, thatRestricted);
  }

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
