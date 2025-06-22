public int read(byte[] buf, int offset, int numToRead) throws IOException {
    int totalRead = 0;

    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = Math.min(numToRead, available());

    while (totalRead < numToRead) {
        int bytesRead = is.read(buf, offset + totalRead, numToRead - totalRead);
        if (bytesRead == -1) {
            hasHitEOF = true;
            if (totalRead == 0) {
                return -1;
            } else {
                break;
            }
        }
        count(bytesRead);
        totalRead += bytesRead;
        entryOffset += bytesRead;
    }

    return totalRead;
}
