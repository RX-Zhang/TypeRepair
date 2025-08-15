public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();
    // Print the header immediately if defined
    final String[] header = format.getHeader();
    if (header != null) {
        printRecord((Object[]) header);
    }
    newRecord = true;
}
