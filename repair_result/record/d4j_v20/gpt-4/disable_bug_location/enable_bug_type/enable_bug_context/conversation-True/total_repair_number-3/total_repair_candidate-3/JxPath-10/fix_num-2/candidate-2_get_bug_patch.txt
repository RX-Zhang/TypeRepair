public final Object computeValue(EvalContext context) {
    return Boolean.valueOf(compute(args[0].computeValue(context), args[1].computeValue(context)));
}
