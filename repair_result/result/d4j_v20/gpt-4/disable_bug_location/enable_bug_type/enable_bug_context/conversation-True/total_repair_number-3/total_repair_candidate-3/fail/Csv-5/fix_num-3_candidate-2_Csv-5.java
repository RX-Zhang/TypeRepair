public void println() throws IOException {
    if (!newRecord) {
        final String recordSeparator = format.getRecordSeparator();
        out.append(recordSeparator);
        newRecord = true;
    }
}
