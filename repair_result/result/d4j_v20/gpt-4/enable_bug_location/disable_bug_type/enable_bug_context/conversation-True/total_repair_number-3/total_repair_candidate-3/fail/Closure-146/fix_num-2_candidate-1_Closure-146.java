public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that instanceof UnionType) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // other types
  switch (this.testForEquality(that)) {
    case TRUE:
      return new TypePair(null, null);

    case FALSE:
    case UNKNOWN:
      // Handle void and unknown types specially to match expected behavior
      if (this.isVoidType() && that.isUnknownType()) {
        return new TypePair(that, that);
      }
      if (that.isVoidType() && this.isUnknownType()) {
        return new TypePair(this, this);
      }
      return new TypePair(this, that);
  }

  // switch case is exhaustive
  throw new IllegalStateException();
}
