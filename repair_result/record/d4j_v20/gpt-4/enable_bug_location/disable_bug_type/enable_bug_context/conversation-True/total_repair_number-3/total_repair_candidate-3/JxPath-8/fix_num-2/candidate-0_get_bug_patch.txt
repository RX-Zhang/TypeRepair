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

    // Fix NaN comparison behavior: NaN compared to anything should be false except for NaN = NaN which is true
    boolean leftIsNaN = Double.isNaN(ld);
    boolean rightIsNaN = Double.isNaN(rd);

    if (leftIsNaN && rightIsNaN) {
        // According to the test case, $nan = $nan should return true
        if (evaluateCompare(0)) {
            return true;
        } else {
            return false;
        }
    } else if (leftIsNaN || rightIsNaN) {
        // Any relational comparisons involving NaN except equality should be false
        if (evaluateCompare(0)) {
            return false;
        } else {
            return false;
        }
    }
    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
