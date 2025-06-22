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
        int readNow = is.read(buf, offset + totalRead, numToRead - totalRead);
        if (readNow == -1) {
            hasHitEOF = true;
            if (entryOffset + totalRead < entrySize) {
                throw new IOException("Truncated entry detected");
            }
            break;
        }
        count(readNow);
        entryOffset += readNow;
        totalRead += readNow;
    }

    return totalRead == 0 ? -1 : totalRead;
}
