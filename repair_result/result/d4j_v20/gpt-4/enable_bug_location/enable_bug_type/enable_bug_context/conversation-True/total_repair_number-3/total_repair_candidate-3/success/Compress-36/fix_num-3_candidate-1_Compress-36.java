private InputStream getCurrentStream() throws IOException {
    if (deferredBlockStreams.isEmpty()) {
        // Defensive fix: return an empty stream instead of throwing,
        // handling the case where no current 7z entry is set.
        return new BoundedInputStream(new ByteArrayInputStream(new byte[0]), 0);
    }
    
    while (deferredBlockStreams.size() > 1) {
        // In solid compression mode we need to decompress all leading folder
        // streams to get access to an entry. We defer this until really needed
        // so that entire blocks can be skipped without wasting time for decompression.
        final InputStream stream = deferredBlockStreams.remove(0);
        IOUtils.skip(stream, Long.MAX_VALUE);
        stream.close();
    }

    return deferredBlockStreams.get(0);
}
