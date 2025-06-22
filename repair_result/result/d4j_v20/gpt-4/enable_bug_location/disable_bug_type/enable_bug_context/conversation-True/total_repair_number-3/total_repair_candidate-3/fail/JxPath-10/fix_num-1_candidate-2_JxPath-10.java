public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);

    // Handle empty node set cases returning false for relational comparisons
    if ((left instanceof Iterator && !((Iterator) left).hasNext()) ||
        (right instanceof Iterator && !((Iterator) right).hasNext())) {
        return Boolean.FALSE;
    }

    return compute(left, right) ? Boolean.TRUE : Boolean.FALSE;
}
