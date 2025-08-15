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

    if (hasHitEOF) {
        currEntry = null;
        return null;
    }

    // Defensive check for TAR header validity: parse entry strictly
    try {
        currEntry = new TarArchiveEntry(headerBuf);
    } catch (IllegalArgumentException e) {
        // Wrap and rethrow, so caller can catch more specific cause
        throw new IOException("Invalid tar header", e);
    }

    entryOffset = 0;
    entrySize = currEntry.getSize();

    if (currEntry.isGNULongNameEntry()) {
        // read in the name
        StringBuilder longName = new StringBuilder();
        byte[] buf = new byte[SMALL_BUFFER_SIZE];
        int length;
        while ((length = read(buf)) >= 0) {
            longName.append(new String(buf, 0, length, "UTF-8"));
        }
        getNextTarEntry(); // Correct recursive call to get next entry, not getNextEntry()
        if (currEntry == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            return null;
        }
        // remove trailing null terminator if present
        int lastCharIndex = longName.length() - 1;
        if (lastCharIndex >= 0 && longName.charAt(lastCharIndex) == '\0') {
            longName.deleteCharAt(lastCharIndex);
        }
        currEntry.setName(longName.toString());
    }

    if (currEntry.isPaxHeader()){ // Process Pax headers
        paxHeaders();
    }

    if (currEntry.isGNUSparse()){ // Process sparse files
        readGNUSparse();
    }

    // If the size of the next element in the archive has changed
    // due to a new size being reported in the posix header
    // information, we update entrySize here so that it contains
    // the correct value.
    entrySize = currEntry.getSize();
    return currEntry;
}
