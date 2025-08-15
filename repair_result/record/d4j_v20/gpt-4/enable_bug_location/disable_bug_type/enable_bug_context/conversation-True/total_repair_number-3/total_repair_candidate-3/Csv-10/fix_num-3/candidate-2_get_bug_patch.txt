public CSVPrinter(final Appendable out, final CSVFormat format) throws IOException {
    Assertions.notNull(out, "out");
    Assertions.notNull(format, "format");

    this.out = out;
    this.format = format;
    this.format.validate();
    this.newRecord = true;

    // Print the header if defined
    if (this.format.getHeader() != null) {
        printRecord(Arrays.asList(this.format.getHeader()));
    }
}
