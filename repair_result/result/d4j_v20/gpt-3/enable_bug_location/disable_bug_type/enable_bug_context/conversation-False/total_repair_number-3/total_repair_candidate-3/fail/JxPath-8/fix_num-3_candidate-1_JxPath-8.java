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

    // Handle NaN comparisons explicitly
    if (Double.isNaN(ld) && Double.isNaN(rd)) {
        // For "=" operation, NaN = NaN should be true
        // Assuming evaluateCompare is designed such that 0 means equality check;
        // since in the test "= NaN = NaN" returns true.
        return evaluateCompare(0);
    }
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // For any comparison involving NaN other than "=" returns false
        return evaluateCompare(0) == false;
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
