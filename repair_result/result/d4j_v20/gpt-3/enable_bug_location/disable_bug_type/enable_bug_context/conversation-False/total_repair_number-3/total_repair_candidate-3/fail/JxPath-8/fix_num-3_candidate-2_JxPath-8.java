private boolean compute(Object left, Object right) {
    left = reduce(left);
    right = reduce(right);

    if (left instanceof InitialContext) {
        ((InitialContext) left).reset();
    }
    if (right instanceof InitialContext) {
        ((InitialContext) right).reset();
    }
    if (left instanceof Iterator && right instanceof Iterator) {
        return findMatch((Iterator) left, (Iterator) right);
    }
    if (left instanceof Iterator) {
        return containsMatch((Iterator) left, right);
    }
    if (right instanceof Iterator) {
        return containsMatch((Iterator) right, left);
    }
    double ld = InfoSetUtil.doubleValue(left);
    double rd = InfoSetUtil.doubleValue(right);

    // Handle NaN according to XPath semantics:
    // Comparison involving NaN always returns false except for "=" which returns false unless both are NaN and equality returns true
    boolean ldNaN = Double.isNaN(ld);
    boolean rdNaN = Double.isNaN(rd);

    if (ldNaN || rdNaN) {
        // For equality (=), return true if both are NaN, else false
        if (operator == Operator.EQUAL) { // Assuming 'operator' field or similar exists to know the compare operator
            return ldNaN && rdNaN;
        }
        return false;
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
