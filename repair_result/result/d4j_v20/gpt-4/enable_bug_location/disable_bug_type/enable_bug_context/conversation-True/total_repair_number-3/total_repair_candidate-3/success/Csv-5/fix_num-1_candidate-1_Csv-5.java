public void println() throws IOException {
    String recordSeparator = format.getRecordSeparator();
    if (recordSeparator == null) {
        recordSeparator = System.lineSeparator();
    }
    out.append(recordSeparator);
    newRecord = true;
}
