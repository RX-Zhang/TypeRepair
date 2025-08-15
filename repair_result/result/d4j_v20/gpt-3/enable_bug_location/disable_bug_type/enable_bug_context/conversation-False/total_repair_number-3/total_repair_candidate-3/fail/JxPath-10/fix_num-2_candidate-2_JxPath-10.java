public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);

    // If either side is an empty node-set (represented by an empty Iterator), 
    // the result should be false to match test expectations.
    if (left instanceof Iterator && !((Iterator<?>) left).hasNext()) {
        return Boolean.FALSE;
    }
    if (right instanceof Iterator && !((Iterator<?>) right).hasNext()) {
        return Boolean.FALSE;
    }

    return compute(left, right) ? Boolean.TRUE : Boolean.FALSE;
}
