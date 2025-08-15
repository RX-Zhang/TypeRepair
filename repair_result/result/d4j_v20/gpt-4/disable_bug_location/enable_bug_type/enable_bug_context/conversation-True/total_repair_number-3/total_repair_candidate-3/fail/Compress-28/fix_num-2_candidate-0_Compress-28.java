public int read(byte[] buf, int offset, int numToRead) throws IOException {
	int totalRead = 0;

    if (hasHitEOF || currEntry == null || entryOffset >= entrySize) {
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

    if (entryOffset >= entrySize) {
        consumeRemainderOfLastBlock();
        setAtEOF(true);
    }

    return totalRead;
}
