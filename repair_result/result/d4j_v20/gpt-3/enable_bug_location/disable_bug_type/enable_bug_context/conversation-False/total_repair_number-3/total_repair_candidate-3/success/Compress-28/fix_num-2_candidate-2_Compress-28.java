public int read(byte[] buf, int offset, int numToRead) throws IOException {
    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = (int) Math.min(numToRead, entrySize - entryOffset);
    int totalRead = is.read(buf, offset, numToRead);
    
    if (totalRead == -1) {
        hasHitEOF = true;
        // If we hit EOF before reading expected entrySize bytes, signal an error
        if (entryOffset < entrySize) {
            throw new IOException("Truncated TAR archive entry");
        }
        return -1;
    }
    
    count(totalRead);
    entryOffset += totalRead;

    return totalRead;
}
