public void println() throws IOException {
    final String recordSeparator = format.getRecordSeparator();
    out.append(recordSeparator == null ? "\n" : recordSeparator);
    newRecord = true;
}
