public int read(byte[] buf, int offset, int numToRead) throws IOException {
    int totalRead = 0;

    if (hasHitEOF) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    if (entryOffset >= entrySize) {
        consumeRemainderOfLastBlock();
        setAtEOF(true);
        return -1;
    }

    numToRead = Math.min(numToRead, available());

    totalRead = is.read(buf, offset, numToRead);
    count(totalRead);

    if (totalRead == -1) {
        setAtEOF(true);
    } else {
        entryOffset += totalRead;
    }

    return totalRead;
}
