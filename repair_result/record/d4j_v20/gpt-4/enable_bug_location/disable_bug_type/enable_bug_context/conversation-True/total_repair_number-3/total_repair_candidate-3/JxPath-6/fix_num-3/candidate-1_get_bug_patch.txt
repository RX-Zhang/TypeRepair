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

    if ((l instanceof Boolean && r instanceof Boolean) ||
        (l == null && r == null)) {
        return equal(l, r);
    }

    if (l instanceof String || r instanceof String) {
        return equal(
            convertToString(l),
            convertToString(r));
    }

    if (l instanceof Number || r instanceof Number) {
        Double ld = convertToDouble(l);
        Double rd = convertToDouble(r);
        return ld.equals(rd);
    }

    return equal(l, r);
}

private String convertToString(Object o) {
    if (o == null) {
        return null;
    }
    return o.toString();
}

private Double convertToDouble(Object o) {
    if (o instanceof Number) {
        return ((Number) o).doubleValue();
    }
    if (o instanceof String) {
        try {
            return Double.valueOf((String) o);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }
    if (o instanceof Boolean) {
        return ((Boolean) o) ? 1.0 : 0.0;
    }
    return Double.NaN;
}
