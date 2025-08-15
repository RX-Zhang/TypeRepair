public TarArchiveEntry getNextTarEntry() throws IOException {
    if (hasHitEOF) {
        return null;
    }

    if (currEntry != null) {
        long numToSkip = entrySize - entryOffset;

        while (numToSkip > 0) {
            long skipped = skip(numToSkip);
            if (skipped <= 0) {
                throw new RuntimeException("failed to skip current tar entry");
            }
            numToSkip -= skipped;
        }

        readBuf = null;
    }

    byte[] headerBuf = getRecord();

    if (headerBuf == null) {
        currEntry = null;
        hasHitEOF = true;
        return null;
    }

    currEntry = new TarArchiveEntry(headerBuf);
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length = 0;
        while ((length = read(buf, 0, buf.length)) >= 0) {
            longName.append(new String(buf, 0, length, "ISO-8859-1"));
        }

        // Move to the next entry header after reading the long name entry data
        TarArchiveEntry nextEntry = getNextTarEntry();

        if (nextEntry == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            currEntry = null;
            hasHitEOF = true;
            return null;
        }
        currEntry = nextEntry;

        // remove trailing null terminator if present
        int lastCharIndex = longName.length() - 1;
        if (lastCharIndex >= 0 && longName.charAt(lastCharIndex) == '\0') {
            longName.deleteCharAt(lastCharIndex);
        }
        currEntry.setName(longName.toString());
        entryOffset = 0;
        entrySize = currEntry.getSize();
    }

    if (currEntry.isPaxHeader()) { // Process Pax headers
        paxHeaders();
    }

    if (currEntry.isGNUSparse()) { // Process sparse files
        readGNUSparse();
    }

    // Update entrySize after processing headers (e.g. pax header adjustments)
    entrySize = currEntry.getSize();

    return currEntry;
}
