public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();

    // If a header is defined, print it immediately upon construction
    final String[] header = format.getHeader();
    if (header != null && header.length > 0) {
        printRecord(Arrays.asList(header));
    }
}
