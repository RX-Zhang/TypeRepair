public int read(byte[] buf, int offset, int numToRead) throws IOException {
	int totalRead = 0;

    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = Math.min(numToRead, available());
    
    totalRead = is.read(buf, offset, numToRead);
    
    if (totalRead == -1) {
        hasHitEOF = true;
        // If entryOffset is less than entrySize when EOF is reached,
        // it means the entry is truncated -> throw IOException
        if (entryOffset < entrySize) {
            throw new IOException("Truncated entry: expected " + entrySize + " bytes, but only read " + entryOffset);
        }
    } else {
        entryOffset += totalRead;
    }

    count(totalRead);
    return totalRead;
}
