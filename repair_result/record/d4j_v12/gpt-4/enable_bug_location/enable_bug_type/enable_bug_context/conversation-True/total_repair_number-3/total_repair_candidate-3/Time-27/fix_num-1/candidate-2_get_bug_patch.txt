private static PeriodFormatter toFormatter(List<Object> elementPairs, boolean notPrinter, boolean notParser) {
    if (notPrinter && notParser) {
        throw new IllegalStateException("Builder has created neither a printer nor a parser");
    }
    int size = elementPairs.size();
    if (size >= 2 && elementPairs.get(0) instanceof Separator) {
        Separator sep = (Separator) elementPairs.get(0);
            PeriodFormatter f = toFormatter(elementPairs.subList(2, size), notPrinter, notParser);
            sep = sep.finish(f.getPrinter(), f.getParser());
            return new PeriodFormatter(sep, sep);
    }
    // Defensive copy and validation before creating composite
    if (elementPairs == null || elementPairs.isEmpty()) {
        throw new IllegalArgumentException("Element pairs must not be null or empty");
    }

    // Validate that elementPairs contains only expected element types
    for (Object elem : elementPairs) {
        if (elem == null) {
            throw new IllegalArgumentException("Element pairs must not contain null");
        }
        // Further validation can be added here based on expected element classes
    }

    Object[] comp = createComposite(elementPairs);
    if (notPrinter) {
        return new PeriodFormatter(null, (PeriodParser) comp[1]);
    } else if (notParser) {
        return new PeriodFormatter((PeriodPrinter) comp[0], null);
    } else {
        return new PeriodFormatter((PeriodPrinter) comp[0], (PeriodParser) comp[1]);
    }
}
