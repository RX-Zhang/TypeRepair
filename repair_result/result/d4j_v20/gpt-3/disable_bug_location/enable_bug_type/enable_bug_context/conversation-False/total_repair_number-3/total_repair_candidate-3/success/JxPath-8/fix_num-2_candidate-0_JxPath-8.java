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

    // Handle NaN comparisons according to XPath semantics:
    // NaN compared with anything (including NaN) is always false for > and <,
    // but for =, NaN = NaN should be true.
    boolean ldIsNaN = Double.isNaN(ld);
    boolean rdIsNaN = Double.isNaN(rd);

    if (ldIsNaN || rdIsNaN) {
        // Determine which comparison is being evaluated by the operator.
        // Since this method returns boolean and calls evaluateCompare,
        // the logic for >, <, = is presumably inside evaluateCompare(int).
        // But we cannot tell the operator here, so let's do the following:
        // If both are NaN, return true only if operator is "=" (i.e. evaluateCompare(0))
        // For other comparisons return false.
        //
        // Since currently evaluateCompare is called with -1, 0, or 1,
        // and the caller uses that for different comparison operators,
        // we return evaluateCompare only if no NaN is present.
        //
        // So here, when NaN is involved,
        // for equality check return (ldIsNaN && rdIsNaN) ? true : false,
        // for other operators return false.
        //
        // But we don't have operator info here. So we change the logic:
        // If NaN is present, and comparison is equality (=), 
        // then ld == rd ? 0 : ... will be 0 only when both values are NaN.
        // But in current code ld == rd is false when NaN, so returns evaluateCompare(1 or -1)
        // which evaluates to true for > or < incorrectly.
        //
        // So to fix, explicitly detect NaN and if both ld and rd are NaN return true,
        // else return false.

        if (ldIsNaN && rdIsNaN) {
            return evaluateCompare(0); // equality
        }
        return false;
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
