public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();

    // Print the header immediately if one is defined
    final String[] headers = format.getHeader();
    if (headers != null) {
        printRecord(java.util.Arrays.asList(headers));
    }
}
