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
        if (contains((Iterator) l, r)) {
            return true;
        }
        if (right instanceof Expression && ((Expression) right).compute(context) instanceof Iterator) {
            return findMatch((Iterator) l, (Iterator) ((Expression) right).compute(context));
        }
        return false;
    }
    if (!(l instanceof Iterator) && (r instanceof Iterator)) {
        if (contains((Iterator) r, l)) {
            return true;
        }
        if (left instanceof Expression && ((Expression) left).compute(context) instanceof Iterator) {
            return findMatch((Iterator) ((Expression) left).compute(context), (Iterator) r);
        }
        return false;
    }
    if (l instanceof Iterator && r instanceof Iterator) {
        return findMatch((Iterator) l, (Iterator) r);
    }
    return equal(l, r);
}
