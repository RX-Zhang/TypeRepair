public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);

    // Added checks to return false if either left or right is an empty node-set representation
    // Assuming empty node-set is represented by either null or an empty Iterator
    if (isEmptyNodeSet(left) || isEmptyNodeSet(right)) {
        return Boolean.FALSE;
    }

    return compute(left, right) ? Boolean.TRUE : Boolean.FALSE;
}

private boolean isEmptyNodeSet(Object obj) {
    if (obj == null) {
        return true;
    }
    if (obj instanceof Iterator) {
        return !((Iterator<?>) obj).hasNext();
    }
    return false;
}
