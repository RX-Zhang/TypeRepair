public int read(byte[] buf, int offset, int numToRead) throws IOException {
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = (int) Math.min(numToRead, entrySize - entryOffset);
    if (numToRead == 0) {
        return -1;
    }

    int totalRead = is.read(buf, offset, numToRead);

    if (totalRead == -1) {
        // Unexpected EOF in the middle of an entry: signal error
        throw new IOException("Truncated entry detected");
    }

    count(totalRead);
    entryOffset += totalRead;

    return totalRead;
}
