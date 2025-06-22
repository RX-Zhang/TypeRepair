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
    
    // If either value is NaN, per XPath spec, all relational comparisons except = return false,
    // but = returns false as well (XPath defines NaN != NaN)
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // For equality (=), we should return false if either is NaN
        // So all comparisons involving NaN should return false here
        return false;
    }
    
    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
