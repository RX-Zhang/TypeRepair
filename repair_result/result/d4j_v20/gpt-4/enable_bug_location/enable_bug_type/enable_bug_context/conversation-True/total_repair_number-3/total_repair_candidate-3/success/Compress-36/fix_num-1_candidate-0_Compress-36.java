private InputStream getCurrentStream() throws IOException {
    if (deferredBlockStreams.isEmpty()) {
        // Instead of throwing, return an empty stream to handle entries with zero size gracefully 
        return new ByteArrayInputStream(new byte[0]);
    }
    
    while (deferredBlockStreams.size() > 1) {
        // In solid compression mode we need to decompress all leading folder'
        // streams to get access to an entry. We defer this until really needed
        // so that entire blocks can be skipped without wasting time for decompression.
        final InputStream stream = deferredBlockStreams.remove(0);
        IOUtils.skip(stream, Long.MAX_VALUE);
        stream.close();
    }

    return deferredBlockStreams.get(0);
}
