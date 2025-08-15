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

    // Fix for NaN handling: comparisons involving NaN should return false except NaN = NaN returns true
    boolean ldNaN = Double.isNaN(ld);
    boolean rdNaN = Double.isNaN(rd);
    if (ldNaN || rdNaN) {
        // For equals: NaN == NaN should return true, else false
        if (ldNaN && rdNaN) {
            return evaluateCompare(0);
        } else {
            // If one is NaN and the other not, always return false
            return false;
        }
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
