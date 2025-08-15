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

    // Fix: When one side is Iterator and the other a single value,
    // contains() should be called only on Iterator with single value argument.
    if ((l instanceof Iterator) && !(r instanceof Iterator)) {
        return contains((Iterator<?>) l, r);
    }
    if (!(l instanceof Iterator) && (r instanceof Iterator)) {
        return contains((Iterator<?>) r, l);
    }
    if (l instanceof Iterator && r instanceof Iterator) {
        return findMatch((Iterator<?>) l, (Iterator<?>) r);
    }
    // At this point both l and r are single objects; perform normal comparison.

    if (l == null) {
        return r == null;
    }

    // If both are Numbers, compare as numbers to avoid string vs number mismatch
    if (l instanceof Number && r instanceof Number) {
        double dl = ((Number) l).doubleValue();
        double dr = ((Number) r).doubleValue();
        return Double.compare(dl, dr) == 0;
    }

    // Otherwise use equals()
    return l.equals(r);
}
