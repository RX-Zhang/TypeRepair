protected boolean equal(
    EvalContext context,
    Expression left,
    Expression right) 
{
    Object l = left.compute(context);
    Object r = right.compute(context);

    // System.err.println("COMPARING: " +
    //     (l == null ? "null" : l.getClass().getName()) + " " +
    //     (r == null ? "null" : r.getClass().getName()));

    if (l instanceof InitialContext || l instanceof SelfContext) {
        l = ((EvalContext) l).getSingleNodePointer();
    }

    if (r instanceof InitialContext || r instanceof SelfContext) {
        r = ((EvalContext) r).getSingleNodePointer();
    }

    if (l instanceof Collection) {
        l = ((Collection<?>) l).iterator();
    }

    if (r instanceof Collection) {
        r = ((Collection<?>) r).iterator();
    }

    if ((l instanceof Iterator) && !(r instanceof Iterator)) {
        // When left is an iterator and right is a single value,
        // check if right is contained in left
        return contains((Iterator<?>) l, r);
    }
    if (!(l instanceof Iterator) && (r instanceof Iterator)) {
        // When right is an iterator and left is a single value,
        // check if left is contained in right
        return contains((Iterator<?>) r, l);
    }
    if (l instanceof Iterator && r instanceof Iterator) {
        // When both are iterators, check if they share any matching elements
        return findMatch((Iterator<?>) l, (Iterator<?>) r);
    }
    // For scalar comparison - use deep equals to handle nulls and objects
    if (l == r) {
        return true;
    }
    if (l == null || r == null) {
        return false;
    }
    // Use equals method for final comparison
    return l.equals(r);
}
