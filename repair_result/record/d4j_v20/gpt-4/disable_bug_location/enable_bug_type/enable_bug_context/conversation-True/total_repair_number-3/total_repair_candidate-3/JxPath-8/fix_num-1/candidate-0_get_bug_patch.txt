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

    // Fix the NaN comparison behavior:
    // If either ld or rd is NaN, comparisons (>, <) should be false,
    // equality should be false except in the specific case where the 
    // operation is '=' and both are NaN (which should return true).
    boolean ldNaN = Double.isNaN(ld);
    boolean rdNaN = Double.isNaN(rd);
    if (ldNaN || rdNaN) {
        // Only equal if both are NaN
        return evaluateCompare((ldNaN && rdNaN) ? 0 : Integer.MIN_VALUE);
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
