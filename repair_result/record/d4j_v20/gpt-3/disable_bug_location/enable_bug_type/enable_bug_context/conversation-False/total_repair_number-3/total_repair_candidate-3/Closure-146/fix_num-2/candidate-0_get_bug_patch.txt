public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that instanceof UnionType) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // other types
  TernaryValue result = this.testForEquality(that);
  switch (result) {
    case TRUE:
      return new TypePair(null, null);

    case FALSE:
      return new TypePair(this, that);

    case UNKNOWN:
      // For unknown equality result, return (null, null) indicating None
      return new TypePair(null, null);

    default:
      throw new IllegalStateException("Unexpected TernaryValue: " + result);
  }
}
