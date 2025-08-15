public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);

    // Check for empty node sets (empty Iterators) - treat as empty
    if (left instanceof Iterator) {
        Iterator it = (Iterator) left;
        if (!it.hasNext()) {
            return Boolean.FALSE;
        }
    }
    if (right instanceof Iterator) {
        Iterator it = (Iterator) right;
        if (!it.hasNext()) {
            return Boolean.FALSE;
        }
    }

    return compute(left, right) ? Boolean.TRUE : Boolean.FALSE;
}
