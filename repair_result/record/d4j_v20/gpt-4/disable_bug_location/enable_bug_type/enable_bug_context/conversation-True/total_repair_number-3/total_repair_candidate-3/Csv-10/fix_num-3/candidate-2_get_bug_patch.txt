public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();

    // Print the header immediately if it is defined in the format
    final String[] header = format.getHeader();
    if (header != null) {
        printRecord(java.util.Arrays.asList(header));
    } else {
        newRecord = true;
    }
}
