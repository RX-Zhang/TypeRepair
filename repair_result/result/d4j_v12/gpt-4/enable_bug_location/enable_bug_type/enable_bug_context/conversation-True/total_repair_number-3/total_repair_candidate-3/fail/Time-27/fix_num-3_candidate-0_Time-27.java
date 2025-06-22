private static PeriodFormatter toFormatter(List<Object> elementPairs, boolean notPrinter, boolean notParser) {
    if (notPrinter && notParser) {
        throw new IllegalStateException("Builder has created neither a printer nor a parser");
    }
    // Validate elementPairs for null and empty
    if (elementPairs == null || elementPairs.isEmpty()) {
        throw new IllegalArgumentException("Element pairs must not be null or empty");
    }
    int size = elementPairs.size();
    if (size >= 2 && elementPairs.get(0) instanceof Separator) {
        Separator sep = (Separator) elementPairs.get(0);
        // Defensive check that subList indices are valid
        if (size < 3) {
            throw new IllegalArgumentException("Element pairs list too short after separator");
        }
        PeriodFormatter f = toFormatter(elementPairs.subList(2, size), notPrinter, notParser);
        sep = sep.finish(f.getPrinter(), f.getParser());
        return new PeriodFormatter(sep, sep);
    }
    Object[] comp = createComposite(elementPairs);
    if (comp == null || comp.length != 2) {
        throw new IllegalStateException("Composite creation failed or returned invalid components");
    }
    if (notPrinter && comp[1] == null) {
        throw new IllegalStateException("Parser is null when printer is disabled");
    }
    if (notParser && comp[0] == null) {
        throw new IllegalStateException("Printer is null when parser is disabled");
    }
    if (notPrinter) {
        return new PeriodFormatter(null, (PeriodParser) comp[1]);
    } else if (notParser) {
        return new PeriodFormatter((PeriodPrinter) comp[0], null);
    } else {
        return new PeriodFormatter((PeriodPrinter) comp[0], (PeriodParser) comp[1]);
    }
}
