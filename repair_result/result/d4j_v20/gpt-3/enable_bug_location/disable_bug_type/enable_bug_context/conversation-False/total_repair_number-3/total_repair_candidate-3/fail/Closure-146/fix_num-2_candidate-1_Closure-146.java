public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that instanceof UnionType) {
    TypePair p = ((UnionType) that).getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // other types
  switch (this.testForEquality(that)) {
    case TRUE:
      return new TypePair(null, null);

    case FALSE:
    case UNKNOWN:
      return new TypePair(this, that);

    default:
      throw new IllegalStateException();
  }
}
