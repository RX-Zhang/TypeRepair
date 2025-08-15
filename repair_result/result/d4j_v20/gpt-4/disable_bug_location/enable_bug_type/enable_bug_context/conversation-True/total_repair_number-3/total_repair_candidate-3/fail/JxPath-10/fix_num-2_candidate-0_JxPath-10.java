public final Object computeValue(EvalContext context) {
    boolean result = compute(args[0].computeValue(context), args[1].computeValue(context));
    return Boolean.valueOf(result);
}
