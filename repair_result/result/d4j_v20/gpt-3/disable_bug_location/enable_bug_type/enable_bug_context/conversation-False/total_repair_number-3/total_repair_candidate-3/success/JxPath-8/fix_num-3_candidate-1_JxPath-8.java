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

    // Handle NaN cases to fix logical error:
    boolean ldIsNaN = Double.isNaN(ld);
    boolean rdIsNaN = Double.isNaN(rd);
    if (ldIsNaN || rdIsNaN) {
        // According to XPath spec and test cases:
        // $nan = $nan should be true only if both are NaN, else false for other relations
        if (evaluateCompare(0)) { // equals
            return ldIsNaN && rdIsNaN;
        } else {
            // For all other comparisons involving NaN, return false
            return false;
        }
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
