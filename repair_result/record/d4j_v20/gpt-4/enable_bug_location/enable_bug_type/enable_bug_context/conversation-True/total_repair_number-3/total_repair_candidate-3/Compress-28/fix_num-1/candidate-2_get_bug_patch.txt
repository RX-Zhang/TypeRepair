public int read(byte[] buf, int offset, int numToRead) throws IOException {
	int totalRead = 0;

    if (isAtEOF() || entryOffset >= entrySize) {
        setAtEOF(true);
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = (int) Math.min(numToRead, entrySize - entryOffset);
    totalRead = is.read(buf, offset, numToRead);
    count(totalRead);

    if (totalRead == -1) {
        setAtEOF(true);
    } else {
        entryOffset += totalRead;
    }

    if (entryOffset >= entrySize) {
        // Consume any remaining bytes and align to the next record boundary
        consumeRemainderOfLastBlock();
    }

    return totalRead;
}
