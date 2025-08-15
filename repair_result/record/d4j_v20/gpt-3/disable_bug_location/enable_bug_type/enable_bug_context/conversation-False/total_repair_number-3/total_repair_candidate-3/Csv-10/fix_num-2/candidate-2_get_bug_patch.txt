public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();

    // Print header immediately if it is defined in the format
    if (format.getHeader() != null && format.getHeader().length > 0) {
        printRecord((Object[]) format.getHeader());
    } else {
        newRecord = true;
    }
}
