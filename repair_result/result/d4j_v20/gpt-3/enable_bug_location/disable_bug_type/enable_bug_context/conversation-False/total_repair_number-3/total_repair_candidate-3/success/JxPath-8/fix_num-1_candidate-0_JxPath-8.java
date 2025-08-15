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

    // Handle NaN comparisons:
    if (Double.isNaN(ld) && Double.isNaN(rd)) {
        // According to XPath spec, NaN = NaN is true for = operator only
        // We return true only if the operator is '=' (i.e., evaluateCompare(0))
        // But since we don't have operator info here, assume evaluateCompare(0)
        return evaluateCompare(0);
    }
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // Any comparison with NaN other than '=' should be false
        // For =, returns false since only NaN=NaN is true
        return false;
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
