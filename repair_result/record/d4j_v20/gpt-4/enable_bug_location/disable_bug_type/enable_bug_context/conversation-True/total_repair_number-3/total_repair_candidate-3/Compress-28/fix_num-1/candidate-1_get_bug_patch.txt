public int read(byte[] buf, int offset, int numToRead) throws IOException {
    int totalRead = 0;

    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    // Input validation for buffer and offset
    if (buf == null) {
        throw new IllegalArgumentException("Buffer cannot be null");
    }
    if (offset < 0 || offset >= buf.length) {
        throw new IndexOutOfBoundsException("Offset is out of bounds");
    }
    if (numToRead < 0 || numToRead > buf.length - offset) {
        throw new IllegalArgumentException("numToRead is out of bounds");
    }

    numToRead = Math.min(numToRead, available());
    
    totalRead = is.read(buf, offset, numToRead);
    count(totalRead);
    
    if (totalRead == -1) {
        hasHitEOF = true;
    } else {
        entryOffset += totalRead;
    }

    return totalRead;
}
