public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();
    // Print header if it is defined in the format
    if (format.getHeader() != null) {
        printRecord(format.getHeader());
    }
}
