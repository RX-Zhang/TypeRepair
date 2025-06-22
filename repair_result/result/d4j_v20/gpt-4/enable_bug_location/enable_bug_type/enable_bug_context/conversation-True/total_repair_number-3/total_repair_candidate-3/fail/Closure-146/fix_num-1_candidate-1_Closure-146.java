public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that.isUnionType()) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // Handle void and unknown types specially to fix the bug
  if (this.isVoidType() && that.isVoidType()) {
    return new TypePair(null, null);
  }

  if (this.isUnknownType() || that.isUnknownType()) {
    return new TypePair(this.isUnknownType() ? this : null,
                        that.isUnknownType() ? that : null);
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
