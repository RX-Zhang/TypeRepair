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

    if (hasHitEOF) {
        currEntry = null;
        return null;
    }

    try {
        currEntry = new TarArchiveEntry(headerBuf);
    } catch (IllegalArgumentException e) {
        throw new IOException("error parsing tar entry header", e);
    }
    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length = 0;
        while ((length = read(buf)) > 0) {
            longName.append(new String(buf, 0, length, "UTF-8"));
        }
        TarArchiveEntry nextEntry = getNextTarEntry();
        if (nextEntry == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            return null;
        }
        currEntry = nextEntry;
        // remove trailing null terminator
        int lnLength = longName.length();
        if (lnLength > 0 && longName.charAt(lnLength - 1) == '\0') {
            longName.deleteCharAt(lnLength - 1);
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

    // If the size of the next element in the archive has changed
    // due to a new size being reported in the posix header
    // information, we update entrySize here so that it contains
    // the correct value.
    entrySize = currEntry.getSize();
    return currEntry;
}
