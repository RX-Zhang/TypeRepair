public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();

    // Print the header line immediately if the format has a header defined
    final String[] header = this.format.getHeader();
    if (header != null) {
        printRecord((Object[]) header);
    }
}
