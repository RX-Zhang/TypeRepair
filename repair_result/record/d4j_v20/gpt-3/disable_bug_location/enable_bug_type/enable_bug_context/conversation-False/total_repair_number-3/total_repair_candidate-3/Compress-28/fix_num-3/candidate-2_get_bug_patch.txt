public int read(byte[] buf, int offset, int numToRead) throws IOException {
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = Math.min(numToRead, available());
    if (numToRead == 0) {
        // No more bytes available to read in this entry
        return -1;
    }
    
    int totalRead = is.read(buf, offset, numToRead);
    
    if (totalRead == -1) {
        // Unexpected EOF in the middle of reading an entry
        throw new IOException("Truncated entry in tar archive");
    }
    
    count(totalRead);
    entryOffset += totalRead;

    if (entryOffset >= entrySize) {
        hasHitEOF = true;
    }

    return totalRead;
}
