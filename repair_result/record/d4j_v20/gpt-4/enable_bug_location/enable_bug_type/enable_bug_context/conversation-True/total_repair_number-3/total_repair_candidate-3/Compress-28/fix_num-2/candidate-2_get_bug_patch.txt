public int read(byte[] buf, int offset, int numToRead) throws IOException {
    int totalRead = 0;

    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = (int) Math.min(numToRead, entrySize - entryOffset);

    totalRead = is.read(buf, offset, numToRead);
    count(totalRead);

    if (totalRead == -1) {
        hasHitEOF = true;
        skipRecordPadding();
        consumeRemainderOfLastBlock();
    } else {
        entryOffset += totalRead;
    }

    return totalRead;
}
