public final Object computeValue(EvalContext context) {
    Object leftValue = args[0].computeValue(context);
    Object rightValue = args[1].computeValue(context);

    // If either side is an empty Iterator (empty node set), return false immediately
    if (leftValue instanceof Iterator && !((Iterator) leftValue).hasNext()) {
        return Boolean.FALSE;
    }
    if (rightValue instanceof Iterator && !((Iterator) rightValue).hasNext()) {
        return Boolean.FALSE;
    }

    return compute(leftValue, rightValue) ? Boolean.TRUE : Boolean.FALSE;
}
