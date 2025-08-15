public TypePair getTypesUnderInequality(JSType that) {
  // union types
  if (that.isUnionType()) {
    TypePair p = that.getTypesUnderInequality(this);
    return new TypePair(p.typeB, p.typeA);
  }

  // other types
  switch (this.testForEquality(that)) {
    case TRUE:
      return new TypePair(null, null);

    case FALSE:
    case UNKNOWN:
      // if one of the types is void and the other is no type,
      // the result should reflect that 'no type' dominates 'void'
      if ((this.isVoidType() && that.isNoType()) || (that.isVoidType() && this.isNoType())) {
        return new TypePair(that.isNoType() ? that : this, that.isVoidType() ? that : this);
      }
      return new TypePair(this, that);
  }

  // switch case is exhaustive
  throw new IllegalStateException();
}
