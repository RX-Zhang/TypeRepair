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

    // Fix: cast possible XPathValue objects to their underlying values before comparing
    if (l instanceof org.apache.commons.jxpath.ri.compiler.XPathExpression) {
        l = ((org.apache.commons.jxpath.ri.compiler.XPathExpression) l).getValue();
    }
    if (r instanceof org.apache.commons.jxpath.ri.compiler.XPathExpression) {
        r = ((org.apache.commons.jxpath.ri.compiler.XPathExpression) r).getValue();
    }

    return equal(l, r);
}
