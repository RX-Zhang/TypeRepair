private InputStream getCurrentStream() throws IOException {
    if (deferredBlockStreams.isEmpty()) {
        throw new IllegalStateException("No current 7z entry (call getNextEntry() first).");
    }
    
    try {
        while (deferredBlockStreams.size() > 1) {
            // In solid compression mode we need to decompress all leading folder'
            // streams to get access to an entry. We defer this until really needed
            // so that entire blocks can be skipped without wasting time for decompression.
            final InputStream stream = deferredBlockStreams.remove(0);
            IOUtils.skip(stream, Long.MAX_VALUE);
            stream.close();
        }
    } catch (IOException e) {
        // Handle the IOException that may occur during stream operations
        throw new IOException("Error while processing the current stream.", e);
    }

    return deferredBlockStreams.get(0);
}
