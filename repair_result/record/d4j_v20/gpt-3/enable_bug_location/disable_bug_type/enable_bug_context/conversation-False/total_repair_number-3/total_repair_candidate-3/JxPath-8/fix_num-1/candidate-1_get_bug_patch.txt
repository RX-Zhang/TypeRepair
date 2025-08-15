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

    // Handle NaN cases explicitly
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // NaN comparisons are always false except NaN = NaN is true
        return evaluateCompare(Double.isNaN(ld) && Double.isNaN(rd) ? 0 : 1);
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
