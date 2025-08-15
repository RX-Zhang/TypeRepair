public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();

    // Print header immediately if format defines one
    if (format.getHeader() != null) {
        printRecord(format.getHeader());
    }

    this.newRecord = true;
}
