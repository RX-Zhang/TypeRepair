private Node tryFoldShift(Node n, Node left, Node right) {
  if (left.getType() == Token.NUMBER &&
      right.getType() == Token.NUMBER) {

    double lval = left.getDouble();
    double rval = right.getDouble();

    // check ranges. We do not do anything that would clip the double to
    // a 32-bit range, since the user likely does not intend that.
    if (!(lval >= Integer.MIN_VALUE && lval <= Integer.MAX_VALUE)) {
      error(BITWISE_OPERAND_OUT_OF_RANGE, left);
      return n;
    }

    // only the lower 5 bits are used when shifting, so don't do anything
    // if the shift amount is outside [0,32)
    if (!(rval >= 0 && rval < 32)) {
      error(SHIFT_AMOUNT_OUT_OF_BOUNDS, right);
      return n;
    }

    // Convert the numbers to ints
    int lvalInt = (int) lval;
    if (lvalInt != lval) {
      error(FRACTIONAL_BITWISE_OPERAND, left);
      return n;
    }

    int rvalInt = (int) rval;
    if (rvalInt != rval) {
      error(FRACTIONAL_BITWISE_OPERAND, right);
      return n;
    }

    long resultLong;
    double result;

    switch (n.getType()) {
      case Token.LSH:
        resultLong = ((long) lvalInt) << rvalInt;
        // Mask to 32 bits
        result = (double) (resultLong & 0xFFFFFFFFL);
        break;
      case Token.RSH:
        resultLong = lvalInt >> rvalInt;
        result = (double) resultLong;
        break;
      case Token.URSH:
        // JavaScript handles zero shifts on signed numbers differently than
        // Java as Java int can not represent the unsigned 32-bit number
        // where JavaScript can so use a long here.
        resultLong = ((long) lvalInt) & 0xFFFFFFFFL;
        resultLong >>>= rvalInt;
        result = (double) resultLong;
        break;
      default:
        throw new AssertionError("Unknown shift operator: " +
            Node.tokenToName(n.getType()));
    }

    Node newNumber = Node.newNumber(result);
    n.getParent().replaceChild(n, newNumber);
    reportCodeChange();

    return newNumber;
  }

  return n;
}
