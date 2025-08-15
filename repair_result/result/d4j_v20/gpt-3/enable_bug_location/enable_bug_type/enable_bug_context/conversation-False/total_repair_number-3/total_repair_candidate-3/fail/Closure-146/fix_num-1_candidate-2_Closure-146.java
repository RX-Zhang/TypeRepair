public TypePair getTypesUnderInequality(JSType that) {
  // unions types
  if (that instanceof UnionType) {
    TypePair p = that.getTypesUnderInequality(this);
    return p; // Return p as-is, don't swap types
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
