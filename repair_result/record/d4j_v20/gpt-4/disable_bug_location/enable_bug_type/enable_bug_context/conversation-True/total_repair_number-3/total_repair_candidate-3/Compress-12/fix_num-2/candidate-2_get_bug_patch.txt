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

    if (headerBuf == null) {  // Fix: check headerBuf for null instead of hasHitEOF
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
        while ((length = read(buf, 0, buf.length)) >= 0) { // Fix: Use read with all params
            longName.append(new String(buf, 0, length, "UTF-8")); // Fix: specify encoding, prevent issues
        }
        // Instead of getNextEntry(), call getNextTarEntry() to properly update currEntry
        currEntry = getNextTarEntry();
        if (currEntry == null) {
            // Bugzilla: 40334
            // Malformed tar file - long entry name not followed by entry
            return null;
        }
        // remove trailing null terminator
        int lastCharIndex = longName.length() - 1;
        if (lastCharIndex >= 0 && longName.charAt(lastCharIndex) == '\0') { // Fix: check for '\0' char properly
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

    // Update entrySize again since paxHeaders or sparse processing may have changed it
    entrySize = currEntry.getSize();
    return currEntry;
}
