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

    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // Per XPath specification, any comparison involving NaN except "=" returns false,
        // and "=" comparing NaN with NaN returns true.
        if (evaluateCompare == null) {
            // Defensive: if evaluateCompare is not accessible here
            // default behavior: all comparisons with NaN are false except "=", only true if both NaN
            return false;
        }
        if (evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1)) {
            return false;
        }
        // Specifically handle '=' operator with NaN
        // If both are NaN, and operator is '=', result is true.
        // Because ld == rd is false when both are NaN, check Double.isNaN both
        if (Double.isNaN(ld) && Double.isNaN(rd) && evaluateCompare(0)) {
            return true;
        }
        return false;
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
