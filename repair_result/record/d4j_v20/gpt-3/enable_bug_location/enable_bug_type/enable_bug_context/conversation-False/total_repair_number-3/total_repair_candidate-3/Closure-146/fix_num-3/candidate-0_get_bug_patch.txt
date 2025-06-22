public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that instanceof UnionType) {
    TypePair p = ((UnionType) that).getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // other types
  TernaryValue result = this.testForEquality(that);
  if (result == TernaryValue.TRUE) {
    return new TypePair(null, null);
  } else if (result == TernaryValue.FALSE || result == TernaryValue.UNKNOWN) {
    return new TypePair(this, that);
  }

  // switch case is exhaustive
  throw new IllegalStateException();
}
