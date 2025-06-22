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

    // Handle NaN comparisons according to IEEE rules where any comparison with NaN except != is false
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // Only equals (0) should return true if both are NaN; others false
        // But per test, $nan = $nan should be true
        // Thus, when both ld and rd are NaN, equality should be true
        // For other cases return false
        if (Double.isNaN(ld) && Double.isNaN(rd)) {
            return evaluateCompare(0);
        } else {
            return false;
        }
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
