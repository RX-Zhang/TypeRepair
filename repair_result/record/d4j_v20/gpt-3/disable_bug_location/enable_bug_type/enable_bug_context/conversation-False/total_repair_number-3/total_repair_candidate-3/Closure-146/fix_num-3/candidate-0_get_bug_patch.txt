public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that instanceof UnionType) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // other types
  TernaryValue equality = this.testForEquality(that);
  switch (equality) {
    case TRUE:
      return new TypePair(null, null);

    case FALSE:
      return new TypePair(this, that);

    case UNKNOWN:
      // When equality is unknown, it's safer to treat as no narrowing (nulls)
      return new TypePair(null, null);
  }

  // switch case is exhaustive
  throw new IllegalStateException();
}
