public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);

    // If one side is an empty node set (represented as an empty Iterator),
    // the result should be false for all relational operations.
    if ((left instanceof Iterator && !((Iterator<?>) left).hasNext()) ||
        (right instanceof Iterator && !((Iterator<?>) right).hasNext())) {
        return Boolean.FALSE;
    }

    return compute(left, right) ? Boolean.TRUE : Boolean.FALSE;
}
