public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that instanceof UnionType) {
    TypePair p = ((UnionType) that).getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // other types
  TernaryValue equality = this.testForEquality(that);
  if (equality == TernaryValue.TRUE) {
    return new TypePair(null, null);
  }
  if (equality == TernaryValue.FALSE || equality == TernaryValue.UNKNOWN) {
    return new TypePair(this, that);
  }

  // switch case is exhaustive, but above covers all TernaryValue cases
  throw new IllegalStateException();
}
