protected boolean equal(
    EvalContext context,
    Expression left,
    Expression right) 
{
    Object l = left.compute(context);
    Object r = right.compute(context);

//    System.err.println("COMPARING: " +
//        (l == null ? "null" : l.getClass().getName()) + " " +
//        (r == null ? "null" : r.getClass().getName()));

    if (l instanceof InitialContext || l instanceof SelfContext) {
        l = ((EvalContext) l).getSingleNodePointer();
    }

    if (r instanceof InitialContext || r instanceof SelfContext) {
        r = ((EvalContext) r).getSingleNodePointer();
    }

    if (l instanceof Collection) {
        l = ((Collection) l).iterator();
    }

    if (r instanceof Collection) {
        r = ((Collection) r).iterator();
    }

    if ((l instanceof Iterator) && !(r instanceof Iterator)) {
        return contains((Iterator) l, r);
    }
    if (!(l instanceof Iterator) && (r instanceof Iterator)) {
        return contains((Iterator) r, l);
    }
    if (l instanceof Iterator && r instanceof Iterator) {
        return findMatch((Iterator) l, (Iterator) r);
    }

    // Here we fix the equality check to handle the case when one side is a single item and the other is a Collection or Iterator
    // by recursively checking equality for Iterable types on either side.

    if (l instanceof Object[] && !(r instanceof Object[])) {
        for (Object o : (Object[]) l) {
            if (equal(o, r)) {
                return true;
            }
        }
        return false;
    }

    if (!(l instanceof Object[]) && r instanceof Object[]) {
        for (Object o : (Object[]) r) {
            if (equal(l, o)) {
                return true;
            }
        }
        return false;
    }

    return equal(l, r);
}
