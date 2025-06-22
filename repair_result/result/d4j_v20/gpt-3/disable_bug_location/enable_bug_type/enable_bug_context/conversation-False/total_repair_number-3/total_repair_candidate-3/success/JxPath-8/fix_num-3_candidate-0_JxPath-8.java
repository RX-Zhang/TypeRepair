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

    // Handle NaN: if either side is NaN, always return false except for '=' when both are NaN
    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // if equals comparison: true only if both are NaN, else false
        // evaluateCompare should receive 0 if equals, else something else
        // So, detect if this is equals operator by checking evaluateCompare(0) returns true for equal
        // Since evaluateCompare is abstract, we can replicate the logic:
        // Instead, handle by returning false unless both are NaN and evaluating equals

        // To do this, when both NaN and equals queried, return true,
        // otherwise false regardless of operator.

        // So:
        // if both NaN and operator is '=' return true
        // else return false

        // How to distinguish operator '=' here? When ld == rd, evaluateCompare is called with 0

        // But ld==rd will be false if NaN involved.

        // To make this clear, for NaN inputs:
        // return evaluateCompare(0) only if both NaN
        // else false

        if (Double.isNaN(ld) && Double.isNaN(rd)) {
            return evaluateCompare(0);
        } else {
            return false;
        }
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
