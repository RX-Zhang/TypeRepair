public void println() throws IOException {
    final String recordSeparator = format.getRecordSeparator();
    out.append(recordSeparator == null ? System.lineSeparator() : recordSeparator);
    newRecord = true;
}
