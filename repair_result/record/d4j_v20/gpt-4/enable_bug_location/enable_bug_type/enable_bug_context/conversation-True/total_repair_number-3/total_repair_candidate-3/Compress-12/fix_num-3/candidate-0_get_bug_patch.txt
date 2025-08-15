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
            if (length > 0) {
                longName.append(new String(buf, 0, length, "UTF-8"));
            }
        }
        TarArchiveEntry nextEntry = getNextTarEntry();
        if (nextEntry == null) {
            // Bugzilla: 40334
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
    }

    if (currEntry.isPaxHeader()) { // Process Pax headers
        paxHeaders();
    }

    if (currEntry.isGNUSparse()) { // Process sparse files
        readGNUSparse();
    }

    entrySize = currEntry.getSize();

    return currEntry;
}
