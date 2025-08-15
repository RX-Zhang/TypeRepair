public final Object computeValue(EvalContext context) {
    Object leftValue = args[0].computeValue(context);
    Object rightValue = args[1].computeValue(context);

    if (leftValue instanceof NodeSet && ((NodeSet) leftValue).isEmpty()) {
        return Boolean.FALSE;
    }
    if (rightValue instanceof NodeSet && ((NodeSet) rightValue).isEmpty()) {
        return Boolean.FALSE;
    }
    return compute(leftValue, rightValue) ? Boolean.TRUE : Boolean.FALSE;
}
