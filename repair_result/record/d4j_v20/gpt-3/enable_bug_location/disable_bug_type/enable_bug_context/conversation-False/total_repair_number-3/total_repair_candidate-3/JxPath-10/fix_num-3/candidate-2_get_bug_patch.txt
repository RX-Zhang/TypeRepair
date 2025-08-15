public final Object computeValue(EvalContext context) {
    Object leftValue = args[0].computeValue(context);
    Object rightValue = args[1].computeValue(context);

    // If either operand is an empty node-set, per XPath specification, the comparison should be false
    if (leftValue instanceof Iterator && !((Iterator) leftValue).hasNext()) {
        return Boolean.FALSE;
    }
    if (rightValue instanceof Iterator && !((Iterator) rightValue).hasNext()) {
        return Boolean.FALSE;
    }

    return compute(leftValue, rightValue) ? Boolean.TRUE : Boolean.FALSE;
}
