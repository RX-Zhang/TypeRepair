public int read(byte[] buf, int offset, int numToRead) throws IOException {
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = Math.min(numToRead, available());

    int totalRead = 0;
    while (totalRead == 0) {
        int read = is.read(buf, offset, numToRead);
        if (read == -1) {
            if (entryOffset < entrySize) {
                throw new IOException("Truncated TAR entry");
            }
            hasHitEOF = true;
            return -1;
        }
        count(read);
        entryOffset += read;
        totalRead += read;
    }

    return totalRead;
}
