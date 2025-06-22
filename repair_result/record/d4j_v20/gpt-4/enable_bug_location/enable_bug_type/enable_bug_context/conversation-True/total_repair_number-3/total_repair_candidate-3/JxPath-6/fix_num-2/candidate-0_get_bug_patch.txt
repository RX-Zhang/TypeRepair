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
    return equal(l, r);
}

protected boolean contains(Iterator it, Object value) {
    while (it.hasNext()) {
        Object element = it.next();
        if (equal(element, value)) {
            return true;
        }
    }
    return false;
}

protected boolean equal(Object l, Object r) {
    if (l == r) {
        return true;
    }
    if (l == null || r == null) {
        return false;
    }
    if (l instanceof Boolean && r instanceof Boolean) {
        return l.equals(r);
    }
    if (l instanceof Number && r instanceof Number) {
        // Compare numbers as doubles for equality
        return ((Number) l).doubleValue() == ((Number) r).doubleValue();
    }
    if (l instanceof String && r instanceof String) {
        return l.equals(r);
    }
    // Additional equality checks can be added here as needed

    return l.equals(r);
}
