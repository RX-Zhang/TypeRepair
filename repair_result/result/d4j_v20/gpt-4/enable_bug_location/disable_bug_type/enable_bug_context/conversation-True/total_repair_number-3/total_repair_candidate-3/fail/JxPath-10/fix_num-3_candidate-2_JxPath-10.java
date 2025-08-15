public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);
    boolean result = compute(left, right);
    return result ? Boolean.TRUE : Boolean.FALSE;
}
