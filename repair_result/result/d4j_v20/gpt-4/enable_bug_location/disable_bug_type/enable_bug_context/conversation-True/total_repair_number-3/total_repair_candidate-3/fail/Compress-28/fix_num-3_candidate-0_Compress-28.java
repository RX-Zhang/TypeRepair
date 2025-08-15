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
        hasHitEOF = true; // Set EOF flag if an IOException occurs
        throw new IOException("Error reading from input stream", e);
    }

    if (totalRead == -1) {
        hasHitEOF = true;
    } else {
        entryOffset += totalRead;
    }

    return totalRead;
}
