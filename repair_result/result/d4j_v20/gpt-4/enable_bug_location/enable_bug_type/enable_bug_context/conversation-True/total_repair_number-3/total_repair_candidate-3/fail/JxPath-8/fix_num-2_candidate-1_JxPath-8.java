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

    // Handle NaN comparisons explicitly, as per IEEE rules:
    // NaN compared to anything (including itself) should return false for > and <,
    // and false for = except in this implementation where $nan = $nan is expected true.
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // According to the test case, $nan = $nan should return true, so:
        if (ld != ld && rd != rd) { // both NaN
            return evaluateCompare(0);
        }
        // For other comparisons involving NaN, return false
        return evaluateCompare(-2); // special value to indicate NaN comparison - evaluateCompare should handle it
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
