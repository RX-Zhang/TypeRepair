private static PeriodFormatter toFormatter(List<Object> elementPairs, boolean notPrinter, boolean notParser) {
    if (notPrinter && notParser) {
        throw new IllegalStateException("Builder has created neither a printer nor a parser");
    }
    int size = elementPairs.size();
    if (size >= 2 && elementPairs.get(0) instanceof Separator) {
        Separator sep = (Separator) elementPairs.get(0);
        // Validate and sanitize the sublist before recursive call to avoid malformed input errors
        List<Object> subList = elementPairs.subList(2, size);
        if (subList == null || subList.isEmpty()) {
            throw new IllegalArgumentException("Sublist for Separator parsing is empty or null");
        }
        PeriodFormatter f = toFormatter(subList, notPrinter, notParser);
        sep = sep.finish(f.getPrinter(), f.getParser());
        return new PeriodFormatter(sep, sep);
    }

    Object[] comp = createComposite(elementPairs);

    // Additional validation: ensure parser/printer are not null when expected
    if (notPrinter) {
        if (comp[1] == null) {
            throw new IllegalArgumentException("Parser is null when parser is expected");
        }
        return new PeriodFormatter(null, (PeriodParser) comp[1]);
    } else if (notParser) {
        if (comp[0] == null) {
            throw new IllegalArgumentException("Printer is null when printer is expected");
        }
        return new PeriodFormatter((PeriodPrinter) comp[0], null);
    } else {
        if (comp[0] == null || comp[1] == null) {
            throw new IllegalArgumentException("Printer or Parser is null when both are expected");
        }
        return new PeriodFormatter((PeriodPrinter) comp[0], (PeriodParser) comp[1]);
    }
}
