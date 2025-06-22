public int read(byte[] buf, int offset, int numToRead) throws IOException {
    int totalRead = 0;

    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    // Input validation: Check that buf is not null, offset is within bounds, and numToRead is valid
    if (buf == null) {
        throw new IllegalArgumentException("Buffer cannot be null");
    }
    if (offset < 0 || offset >= buf.length) {
        throw new ArrayIndexOutOfBoundsException("Offset is out of bounds");
    }
    if (numToRead < 0 || offset + numToRead > buf.length) {
        throw new ArrayIndexOutOfBoundsException("Number of bytes to read is invalid");
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
