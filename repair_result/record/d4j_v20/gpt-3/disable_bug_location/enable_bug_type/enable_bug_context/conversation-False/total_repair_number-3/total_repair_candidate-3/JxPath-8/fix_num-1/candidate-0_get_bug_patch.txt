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
    // Handle NaN according to XPath rules:
    // Any comparison with NaN is false except equality of NaN = NaN which is true.
    if (Double.isNaN(ld) && Double.isNaN(rd)) {
        // NaN = NaN is true, otherwise false
        return evaluateCompare(0);
    }
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // Comparisons with NaN always false except = handled above
        // Return false for all relational comparisons
        return false;
    }
    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
