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

    if (l instanceof Iterator && !(r instanceof Iterator)) {
        // Iterate l to find r
        while (((Iterator<?>) l).hasNext()) {
            if (equal(((Iterator<?>) l).next(), r)) {
                return true;
            }
        }
        return false;
    }

    if (!(l instanceof Iterator) && r instanceof Iterator) {
        // Iterate r to find l
        while (((Iterator<?>) r).hasNext()) {
            if (equal(l, ((Iterator<?>) r).next())) {
                return true;
            }
        }
        return false;
    }

    if (l instanceof Iterator && r instanceof Iterator) {
        // Find any matching element in the two iterators
        // Collect elements of l
        java.util.List<Object> leftList = new java.util.ArrayList<>();
        while (((Iterator<?>) l).hasNext()) {
            leftList.add(((Iterator<?>) l).next());
        }

        // Iterate r and check for any matches
        while (((Iterator<?>) r).hasNext()) {
            Object rElement = ((Iterator<?>) r).next();
            for (Object leftElement : leftList) {
                if (equal(leftElement, rElement)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Fall back to standard equality
    return (l == null) ? (r == null) : l.equals(r);
}
