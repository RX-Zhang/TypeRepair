private InputStream getCurrentStream() throws IOException {
    if (deferredBlockStreams.isEmpty()) {
        throw new IllegalStateException("No current 7z entry (call getNextEntry() first).");
    }
    
    while (deferredBlockStreams.size() > 1) {
        final InputStream stream = deferredBlockStreams.remove(0);
        IOUtils.skip(stream, Long.MAX_VALUE);
        stream.close();
    }

    if (deferredBlockStreams.get(0) == null) {
        // No data stream available for current entry, try to build it
        buildDecodingStream();
        if (deferredBlockStreams.isEmpty() || deferredBlockStreams.get(0) == null) {
            throw new IllegalStateException("No current 7z entry (call getNextEntry() first).");
        }
    }

    return deferredBlockStreams.get(0);
}
