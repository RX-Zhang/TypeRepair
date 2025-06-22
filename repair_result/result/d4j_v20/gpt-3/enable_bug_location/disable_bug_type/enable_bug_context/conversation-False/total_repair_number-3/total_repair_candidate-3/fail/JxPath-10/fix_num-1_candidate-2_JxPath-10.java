public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);

    if (isEmptyNodeSet(left) || isEmptyNodeSet(right)) {
        return Boolean.FALSE;
    }

    return compute(left, right) ? Boolean.TRUE : Boolean.FALSE;
}

private boolean isEmptyNodeSet(Object obj) {
    if (obj instanceof Iterator) {
        Iterator<?> it = (Iterator<?>) obj;
        return !it.hasNext();
    }
    return false;
}
