static boolean isValidDefineValue(Node val, Set<String> defines) {
  switch (val.getType()) {
    case Token.STRING:
    case Token.NUMBER:
    case Token.TRUE:
    case Token.FALSE:
    case Token.NULL:  // missing in original, allows null literals
      return true;

    // Binary operators are only valid if both children are valid.
    case Token.BITAND:
    case Token.BITOR:
    case Token.BITXOR:
    case Token.LSH:
    case Token.RSH:
    case Token.URSH:
    case Token.ADD:
    case Token.SUB:
    case Token.MUL:
    case Token.DIV:
    case Token.MOD:
    case Token.AND:
    case Token.OR:
    case Token.EQ:
    case Token.NE:
    case Token.SHEQ:
    case Token.SHNE:
    case Token.LT:
    case Token.LE:
    case Token.GT:
    case Token.GE:
      Node left = val.getFirstChild();
      Node right = left.getNext();
      return isValidDefineValue(left, defines) && isValidDefineValue(right, defines);

    // Unary operators are valid if the child is valid.
    case Token.NOT:
    case Token.NEG:
    case Token.BITNOT:
      return isValidDefineValue(val.getFirstChild(), defines);

    // Names are valid if and only if they are defines themselves.
    case Token.NAME:
      String name = val.getString();
      // Some special keywords are invalid.
      if ("null".equals(name) || "undefined".equals(name) || "NaN".equals(name)) {
        return false;
      }
      return defines.contains(name);

    case Token.GETPROP:
      if (val.isQualifiedName()) {
        return defines.contains(val.getQualifiedName());
      }
      return false;
  }
  return false;
}
