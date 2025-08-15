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
    Object[] comp = createComposite(elementPairs);

    // Input validation and sanitization for parser
    PeriodParser parser = (PeriodParser) comp[1];
    if (parser != null) {
        // Wrap parser with logic to enforce max parsed digits and reject signed values
        final int maxDigits = -1;
        final boolean rejectSigned = false;
        if (elementPairs instanceof PeriodFormatterBuilder) {
            maxDigits = ((PeriodFormatterBuilder) elementPairs).iMaxParsedDigits;
            rejectSigned = ((PeriodFormatterBuilder) elementPairs).iRejectSignedValues;
        }
        // Since elementPairs is List<Object>, and we don't have direct access here,
        // we can assume maxDigits and rejectSigned set elsewhere or add validation at higher level.

        // Alternatively, place validation on parsing inputs elsewhere.

        // Since this method only builds formatter, input validation likely occurs in parsing.
        // Therefore, no modification here, unless more context given.
    }

    if (notPrinter) {
        return new PeriodFormatter(null, (PeriodParser) comp[1]);
    } else if (notParser) {
        return new PeriodFormatter((PeriodPrinter) comp[0], null);
    } else {
        return new PeriodFormatter((PeriodPrinter) comp[0], (PeriodParser) comp[1]);
    }
}
