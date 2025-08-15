public TypePair getTypesUnderInequality(JSType that) {
  // unions types
  if (that instanceof UnionType) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // Handle void and unknown types explicitly
  if (this.isVoidType() && that.isUnknownType()) {
    return new TypePair(null, null);
  }
  if (this.isUnknownType() && that.isVoidType()) {
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
