public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);

    // If either side is an empty node set (likely represented as an empty Iterator or InitialContext with no nodes),
    // the result should be false for relational operations.
    // The existing compute method does not correctly handle empty node sets, causing failed tests.
    // So here, explicitly check for empty iterators/InitialContext and return false when empty.
    Object lReduced = reduce(left);
    Object rReduced = reduce(right);

    if (isEmptyIteratorOrInitialContext(lReduced) || isEmptyIteratorOrInitialContext(rReduced)) {
        return Boolean.FALSE;
    }

    return compute(left, right) ? Boolean.TRUE : Boolean.FALSE;
}

// Helper method to reduce and check if empty iterator or InitialContext (empty node set).
private boolean isEmptyIteratorOrInitialContext(Object o) {
    if (o instanceof InitialContext) {
        InitialContext ic = (InitialContext) o;
        ic.reset();
        return !ic.hasNext();
    }
    if (o instanceof Iterator) {
        Iterator<?> it = (Iterator<?>) o;
        return !it.hasNext();
    }
    return false;
}

// Assuming the method reduce(Object) is accessible here as in the compute method context.
