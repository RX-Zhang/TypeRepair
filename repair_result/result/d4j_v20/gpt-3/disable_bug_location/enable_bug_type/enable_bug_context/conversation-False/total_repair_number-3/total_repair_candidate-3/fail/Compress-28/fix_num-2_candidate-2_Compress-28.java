public int read(byte[] buf, int offset, int numToRead) throws IOException {
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = (int) Math.min(numToRead, available());

    int totalRead = is.read(buf, offset, numToRead);
    
    if (totalRead == -1) {
        // EOF encountered in the middle of the entry -> truncated entry
        throw new IOException("Truncated entry");
    }

    count(totalRead);
    entryOffset += totalRead;

    if (entryOffset >= entrySize) {
        hasHitEOF = true;
    }

    return totalRead;
}
