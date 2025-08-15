public final Object computeValue(EvalContext context) {
    Object leftValue = args[0].computeValue(context);
    Object rightValue = args[1].computeValue(context);

    // If either side is an empty node set, per XPath specification, comparisons return false
    if (isEmptyNodeSet(leftValue) || isEmptyNodeSet(rightValue)) {
        return Boolean.FALSE;
    }

    return compute(leftValue, rightValue) ? Boolean.TRUE : Boolean.FALSE;
}

// Helper method to identify empty node sets, assuming node sets are represented as Iterators or InitialContext
private boolean isEmptyNodeSet(Object value) {
    if (value instanceof Iterator) {
        return !((Iterator<?>) value).hasNext();
    }
    if (value instanceof InitialContext) {
        Iterator<?> it = ((InitialContext) value).nodes();
        return !it.hasNext();
    }
    return false;
}
