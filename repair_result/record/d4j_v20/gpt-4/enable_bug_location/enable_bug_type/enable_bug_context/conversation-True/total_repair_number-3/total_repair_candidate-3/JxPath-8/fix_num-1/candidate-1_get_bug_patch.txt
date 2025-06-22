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

    // Handle NaN comparisons explicitly following XPath semantics
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        if (evaluateCompare(0)) {
            // If comparing for equality, NaN = NaN should be true
            return (Double.isNaN(ld) && Double.isNaN(rd));
        } else {
            // For > or < comparisons, should return false if either is NaN
            return false;
        }
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
