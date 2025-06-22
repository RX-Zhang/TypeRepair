public final Object computeValue(EvalContext context) {
    Object leftValue = args[0].computeValue(context);
    Object rightValue = args[1].computeValue(context);
    return compute(leftValue, rightValue) ? Boolean.TRUE : Boolean.FALSE;
}
