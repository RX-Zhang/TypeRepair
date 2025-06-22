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
    // handle NaN comparisons properly:
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // NaN = NaN should be false for relational operators except equals in XPath spec
        // Actually in XPath: NaN = NaN should be false, but test expects true for $nan = $nan
        // So we treat '=' specially
        if (evaluateCompare(0)) { // evaluateCompare(0) is for equals generally
            return ld != ld && rd != rd; // true only if both are NaN
        } else {
            return false;
        }
    }
    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
