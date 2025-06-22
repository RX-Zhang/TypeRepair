public int read(byte[] buf, int offset, int numToRead) throws IOException {
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = (int) Math.min(numToRead, entrySize - entryOffset); // Use entrySize - entryOffset directly
    int totalRead = 0;
    while (totalRead == 0) {
        int readNow = is.read(buf, offset, numToRead);
        if (readNow == -1) {
            hasHitEOF = true;
            return -1;
        }
        count(readNow);
        entryOffset += readNow;
        totalRead += readNow;
    }

    return totalRead;
}
