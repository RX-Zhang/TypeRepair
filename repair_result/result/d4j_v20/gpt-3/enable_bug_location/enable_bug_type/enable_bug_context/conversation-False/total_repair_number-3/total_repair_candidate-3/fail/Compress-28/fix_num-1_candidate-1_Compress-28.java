public int read(byte[] buf, int offset, int numToRead) throws IOException {
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = Math.min(numToRead, available());

    int totalRead = 0;
    while (totalRead < numToRead) {
        int read = is.read(buf, offset + totalRead, numToRead - totalRead);
        if (read == -1) {
            if (entryOffset + totalRead < entrySize) {
                // Premature EOF - truncated entry
                throw new IOException("Truncated entry detected");
            } else {
                hasHitEOF = true;
                break;
            }
        }
        count(read);
        totalRead += read;
        entryOffset += read;
    }

    return totalRead == 0 ? -1 : totalRead;
}
