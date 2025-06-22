public final Object computeValue(EvalContext context) {
    Object left = args[0].computeValue(context);
    Object right = args[1].computeValue(context);

    // Reduce operands first to handle empty node sets correctly
    left = reduce(left);
    right = reduce(right);

    // Check if either operand is an empty node set
    boolean leftIsEmptyNodeSet = (left instanceof Iterator) && !((Iterator) left).hasNext();
    boolean rightIsEmptyNodeSet = (right instanceof Iterator) && !((Iterator) right).hasNext();

    if (leftIsEmptyNodeSet || rightIsEmptyNodeSet) {
        return Boolean.FALSE;
    }

    // Perform the compute operation normally
    return compute(left, right) ? Boolean.TRUE : Boolean.FALSE;
}
