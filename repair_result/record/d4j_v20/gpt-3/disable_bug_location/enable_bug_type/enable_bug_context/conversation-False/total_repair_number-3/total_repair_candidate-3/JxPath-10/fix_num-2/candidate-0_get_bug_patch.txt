public final Object computeValue(EvalContext context) {
    Object leftValue = args[0].computeValue(context);
    Object rightValue = args[1].computeValue(context);

    // If either side is an empty node set (often represented by a special Iterator with no elements),
    // comparisons (except for != and != can be problematic). We handle empty node sets by
    // returning false for all relational operations except != (not equals),
    // which should return true if the right operand is not empty or differs.
    // Since the bug relates to empty node sets incorrectly evaluating to true for >= 0,
    // we must ensure empty node sets do not compare as true unexpectedly.

    // However, the primary logic fix is inside compute method,
    // so here we just call compute

    return compute(leftValue, rightValue) ? Boolean.TRUE : Boolean.FALSE;
}
