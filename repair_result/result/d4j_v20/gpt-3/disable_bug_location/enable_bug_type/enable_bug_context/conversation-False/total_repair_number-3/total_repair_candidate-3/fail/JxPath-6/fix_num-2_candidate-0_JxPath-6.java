protected boolean equal(
    EvalContext context,
    Expression left,
    Expression right) 
{
    Object l = left.compute(context);
    Object r = right.compute(context);

    // Uncomment for debugging comparisons.
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
        return contains((Iterator<?>) l, r);
    }
    if (!(l instanceof Iterator) && (r instanceof Iterator)) {
        return contains((Iterator<?>) r, l);
    }
    if (l instanceof Iterator && r instanceof Iterator) {
        return findMatch((Iterator<?>) l, (Iterator<?>) r);
    }

    // The logical error was that the fallback call equal(l, r) would recursively
    // call this method again causing infinite recursion.
    // We need to call a different equal method that compares two Objects instead of Expressions.
    // Assuming such method exists or use standard equals method.
    if (l == null) {
        return r == null;
    }
    if (r == null) {
        return false;
    }
    return l.equals(r);
}
