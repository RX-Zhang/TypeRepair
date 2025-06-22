public int read(byte[] buf, int offset, int numToRead) throws IOException {
	int totalRead = 0;

    if (hasHitEOF || entryOffset >= entrySize) {
        return -1;
    }

    if (currEntry == null) {
        throw new IllegalStateException("No current tar entry");
    }

    numToRead = Math.min(numToRead, available());

    while (numToRead > 0) {
        int bytesRead = is.read(buf, offset + totalRead, numToRead);
        if (bytesRead == -1) {
            hasHitEOF = true;
            if (totalRead == 0) {
                return -1;
            } else {
                break;
            }
        }
        totalRead += bytesRead;
        entryOffset += bytesRead;
        numToRead -= bytesRead;

        // If we've read as much as is available for the current entry, stop
        if (entryOffset >= entrySize) {
            break;
        }
    }
    count(totalRead);

    return totalRead;
}
