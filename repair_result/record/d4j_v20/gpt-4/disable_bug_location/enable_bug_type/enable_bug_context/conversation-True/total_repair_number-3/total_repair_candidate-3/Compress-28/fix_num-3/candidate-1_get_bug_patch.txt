public int read(byte[] buf, int offset, int numToRead) throws IOException {
	int totalRead = 0;

    if (hasHitEOF || entryOffset >= entrySize) {
        // Consume padding bytes of the last block before hitting EOF
        consumeRemainderOfLastBlock();
        setAtEOF(true);
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = Math.min(numToRead, available());

    totalRead = is.read(buf, offset, numToRead);
    count(totalRead);

    if (totalRead == -1) {
        setAtEOF(true);
        return -1;
    }

    entryOffset += totalRead;

    return totalRead;
}
