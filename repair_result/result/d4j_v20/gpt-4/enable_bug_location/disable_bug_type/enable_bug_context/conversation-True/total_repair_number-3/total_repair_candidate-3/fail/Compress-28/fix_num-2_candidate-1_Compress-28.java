public int read(byte[] buf, int offset, int numToRead) throws IOException {
    int totalRead = 0;

    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    // Input validation for the buffer and offset
    if (buf == null) {
        throw new IllegalArgumentException("Buffer cannot be null");
    }
    if (offset < 0 || offset >= buf.length) {
        throw new ArrayIndexOutOfBoundsException("Offset is out of bounds");
    }
    if (numToRead < 0 || numToRead > available()) {
        numToRead = available(); // Adjust numToRead to available bytes
    }

    totalRead = is.read(buf, offset, numToRead);
    if (totalRead == -1) {
        hasHitEOF = true;
    } else {
        entryOffset += totalRead;
    }
    
    if (totalRead > 0) {
        count(totalRead);
    }

    return totalRead;
}
