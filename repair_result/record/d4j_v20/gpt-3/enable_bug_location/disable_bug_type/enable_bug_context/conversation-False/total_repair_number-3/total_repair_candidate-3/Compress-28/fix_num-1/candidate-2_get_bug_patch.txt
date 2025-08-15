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
            hasHitEOF = true;
            if (totalRead == 0) {
                return -1;
            }
            break;
        }
        count(read);
        totalRead += read;
        entryOffset += read;
    }

    return totalRead;
}
