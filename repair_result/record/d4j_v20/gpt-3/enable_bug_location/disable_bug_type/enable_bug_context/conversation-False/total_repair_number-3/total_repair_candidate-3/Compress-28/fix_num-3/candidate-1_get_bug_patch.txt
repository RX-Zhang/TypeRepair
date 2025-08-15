public int read(byte[] buf, int offset, int numToRead) throws IOException {
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = (int) Math.min(numToRead, entrySize - entryOffset);

    int totalRead = 0;
    while (numToRead > 0) {
        int readLen = is.read(buf, offset, numToRead);
        if (readLen == -1) {
            hasHitEOF = true;
            if (totalRead == 0) {
                return -1;
            }
            break;
        }
        count(readLen);
        offset += readLen;
        totalRead += readLen;
        entryOffset += readLen;
        numToRead -= readLen;
    }

    return totalRead;
}
