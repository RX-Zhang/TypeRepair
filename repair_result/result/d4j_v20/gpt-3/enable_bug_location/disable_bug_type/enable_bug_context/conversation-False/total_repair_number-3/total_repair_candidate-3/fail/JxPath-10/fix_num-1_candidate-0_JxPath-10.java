public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);

    boolean result = compute(left, right);

    // If either left or right is an empty node set (represented by an empty iterator),
    // the result should be false according to the test case expectations.
    if (isEmptyNodeSet(left) || isEmptyNodeSet(right)) {
        return Boolean.FALSE;
    }

    return result ? Boolean.TRUE : Boolean.FALSE;
}

// Helper method to detect empty node sets; assumes node sets are iterators
private boolean isEmptyNodeSet(Object obj) {
    if (obj instanceof Iterator) {
        return !((Iterator<?>) obj).hasNext();
    }
    return false;
}
