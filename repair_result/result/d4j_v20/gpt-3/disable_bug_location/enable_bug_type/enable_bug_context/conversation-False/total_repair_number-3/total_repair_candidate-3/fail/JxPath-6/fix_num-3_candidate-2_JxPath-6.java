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

    if (l instanceof Collection && !(r instanceof Iterator)) {
        l = ((Collection) l).iterator();
    }

    if (r instanceof Collection && !(l instanceof Iterator)) {
        r = ((Collection) r).iterator();
    }

    // If left is Iterator and right is not, check if l contains r
    if ((l instanceof Iterator) && !(r instanceof Iterator)) {
        return contains((Iterator) l, r);
    }
    // If right is Iterator and left is not, check if r contains l
    if (!(l instanceof Iterator) && (r instanceof Iterator)) {
        return contains((Iterator) r, l);
    }
    // If both are Iterators, check if they have any matching element
    if (l instanceof Iterator && r instanceof Iterator) {
        return findMatch((Iterator) l, (Iterator) r);
    }
    return equal(l, r);
}
