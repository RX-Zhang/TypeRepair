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

    // Handle NaN cases explicitly to ensure NaN comparisons behave correctly
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // According to XPath semantics:
        // For = operator: NaN = NaN is true only if evaluateCompare(0) implies equality
        // For < or > operators: comparison involving NaN is false
        // Since we don't have operator info here, we must handle equality by checking if ld and rd are both NaN
        // If both are NaN, treat as equal (0), else not equal
        if (Double.isNaN(ld) && Double.isNaN(rd)) {
            return evaluateCompare(0);
        }
        return evaluateCompare(-2); // return false, use an impossible value to ensure false
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
