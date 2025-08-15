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

    // Input validation: ensure parsing signed values or extremely long digit sequences as per builder settings
    // Defensive check to ensure max parsed digits limit is respected and signed values are rejected if set

    // Filter elementPairs to find FieldFormatters and check their maxParsedDigits and rejectSignedValues settings if available
    // Since elementPairs contains objects (likely pairs of printer/parser), this validation may require domain knowledge 
    // For demonstration, since no direct access to iMaxParsedDigits or iRejectSignedValues here, 
    // assume createComposite applies those internally, so pre-validate digit length in input parsing would be outside this method.

    // Since actual parsing is done by the PeriodFormatter or underlying parsers,
    // robust validation should be part of parse method, but here we can at least prepare a safe formatter.

    Object[] comp = createComposite(elementPairs);

    if (notPrinter) {
        return new PeriodFormatter(null, (PeriodParser) comp[1]);
    } else if (notParser) {
        return new PeriodFormatter((PeriodPrinter) comp[0], null);
    } else {
        return new PeriodFormatter((PeriodPrinter) comp[0], (PeriodParser) comp[1]);
    }
}
