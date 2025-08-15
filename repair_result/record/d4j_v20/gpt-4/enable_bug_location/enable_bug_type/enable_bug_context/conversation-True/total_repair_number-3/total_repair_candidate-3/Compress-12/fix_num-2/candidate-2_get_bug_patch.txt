public TarArchiveEntry getNextTarEntry() throws IOException {
    if (hasHitEOF) {
        return null;
    }

    if (currEntry != null) {
        long numToSkip = entrySize - entryOffset;

        while (numToSkip > 0) {
            long skipped = skip(numToSkip);
            if (skipped <= 0) {
                throw new IOException("failed to skip current tar entry");
            }
            numToSkip -= skipped;
        }

        readBuf = null;
    }

    byte[] headerBuf = getRecord();

    if (headerBuf == null) {
        currEntry = null;
        return null;
    }

    currEntry = new TarArchiveEntry(headerBuf);
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length;
        long remaining = (entrySize > 0) ? entrySize : Long.MAX_VALUE;

        while (remaining > 0 && (length = read(buf, 0, (int) Math.min(buf.length, remaining))) > 0) {
            longName.append(new String(buf, 0, length, "UTF-8"));
            remaining -= length;
        }

        // Skip the GNULongNameEntry header that follows the long name
        TarArchiveEntry nextEntry = getNextTarEntry();
        if (nextEntry == null) {
            // Malformed tar file - long entry name not followed by entry
            return null;
        }
        currEntry = nextEntry;

        // remove trailing null terminator
        int lastIndex = longName.length() - 1;
        if (lastIndex >= 0 && longName.charAt(lastIndex) == '\0') {
            longName.deleteCharAt(lastIndex);
        }
        currEntry.setName(longName.toString());
        entrySize = currEntry.getSize();
        entryOffset = 0;
        readBuf = null;
    }

    if (currEntry.isPaxHeader()) { // Process Pax headers
        paxHeaders();
        entrySize = currEntry.getSize();
        entryOffset = 0;
    }

    if (currEntry.isGNUSparse()) { // Process sparse files
        readGNUSparse();
        entrySize = currEntry.getSize();
        entryOffset = 0;
    }

    return currEntry;
}
