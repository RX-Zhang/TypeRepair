public void println() throws IOException {
    final String recordSeparator = format.getRecordSeparator();
    out.append(recordSeparator != null ? recordSeparator : System.lineSeparator());
    newRecord = true;
}
