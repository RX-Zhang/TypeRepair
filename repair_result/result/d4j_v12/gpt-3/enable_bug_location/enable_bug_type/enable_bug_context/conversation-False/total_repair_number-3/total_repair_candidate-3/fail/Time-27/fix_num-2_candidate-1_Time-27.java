private static PeriodFormatter toFormatter(List<Object> elementPairs, boolean notPrinter, boolean notParser) {
    if (notPrinter && notParser) {
        throw new IllegalStateException("Builder has created neither a printer nor a parser");
    }
    int size = elementPairs.size();
    if (size == 0) {
        // Defensive: return empty formatter if no elements
        return new PeriodFormatter(notPrinter ? null : new PeriodPrinter() { 
            public int countFieldsToPrint(ReadablePeriod readablePeriod, int value) { return 0; }
            public void printTo(StringBuffer buf, ReadablePeriod period, Locale locale) { }
            public void printTo(Writer out, ReadablePeriod period, Locale locale) throws IOException { }
            public int parseInto(ReadWritablePeriod period, String text, int position) { return ~position; }
        }, notParser ? null : new PeriodParser() {
            public int parseInto(ReadWritablePeriod period, String text, int position) { return ~position; }
        });
    }
    if (size >= 2 && elementPairs.get(0) instanceof Separator) {
        Separator sep = (Separator) elementPairs.get(0);
        PeriodFormatter f = toFormatter(elementPairs.subList(2, size), notPrinter, notParser);
        sep = sep.finish(f.getPrinter(), f.getParser());
        return new PeriodFormatter(sep, sep);
    }
    Object[] comp = createComposite(elementPairs);
    if (notPrinter) {
        // Add null checks to avoid ClassCastException if comp[1] is null
        PeriodParser parser = (comp[1] instanceof PeriodParser) ? (PeriodParser) comp[1] : null;
        return new PeriodFormatter(null, parser);
    } else if (notParser) {
        PeriodPrinter printer = (comp[0] instanceof PeriodPrinter) ? (PeriodPrinter) comp[0] : null;
        return new PeriodFormatter(printer, null);
    } else {
        PeriodPrinter printer = (comp[0] instanceof PeriodPrinter) ? (PeriodPrinter) comp[0] : null;
        PeriodParser parser = (comp[1] instanceof PeriodParser) ? (PeriodParser) comp[1] : null;
        return new PeriodFormatter(printer, parser);
    }
}
