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

    if (Double.isNaN(ld) || Double.isNaN(rd)) {
        // According to IEEE, any comparison involving NaN except '!=' is false,
        // but for the relational expressions '<', '>', '=' this should be false.
        // Here we return false, since no ordering can be established.
        return false;
    }

    return evaluateCompare(ld == rd ? 0 : ld < rd ? -1 : 1);
}
