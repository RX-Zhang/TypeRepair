public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();

    // Print header if present at construction
    final String[] headers = format.getHeader();
    if (headers != null) {
        printRecord((Object[]) headers);
    }
}
