public int read(byte[] buf, int offset, int numToRead) throws IOException {
    int totalRead = 0;

    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = Math.min(numToRead, available());

    try {
        totalRead = is.read(buf, offset, numToRead);
        count(totalRead);
    } catch (IOException e) {
        hasHitEOF = true; // Mark EOF if an exception occurs during read
        throw new IOException("Error reading entry data", e);
    }

    if (totalRead == -1) {
        hasHitEOF = true;
    } else {
        entryOffset += totalRead;
    }

    return totalRead;
}
