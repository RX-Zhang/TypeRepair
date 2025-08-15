public int read(byte[] buf, int offset, int numToRead) throws IOException {
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = Math.min(numToRead, available());

    int totalRead = is.read(buf, offset, numToRead);

    if (totalRead == -1) {
        hasHitEOF = true;
        // If we hit EOF before reading all of the entry data, this indicates truncated entry
        if (entryOffset < entrySize) {
            throw new IOException("Truncated TAR archive entry: Expected " + entrySize + " bytes, read " + entryOffset);
        }
        return -1;
    }

    count(totalRead);
    entryOffset += totalRead;

    return totalRead;
}
