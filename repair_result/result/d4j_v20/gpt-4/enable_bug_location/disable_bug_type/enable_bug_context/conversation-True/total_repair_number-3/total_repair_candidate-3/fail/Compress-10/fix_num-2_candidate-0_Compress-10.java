private void resolveLocalFileHeaderData(Map<ZipArchiveEntry, NameAndComment>
                                        entriesWithoutUTF8Flag)
    throws IOException {
    // changing the name of a ZipArchiveEntry is going to change
    // the hashcode - see COMPRESS-164
    // Map needs to be reconstructed in order to keep central
    // directory order

    Map<ZipArchiveEntry, OffsetEntry> newEntries = new LinkedHashMap<>();
    Map<String, ZipArchiveEntry> newNameMap = new HashMap<>();

    for (ZipArchiveEntry ze : entries.keySet()) {
        OffsetEntry offsetEntry = entries.get(ze);
        long offset = offsetEntry.headerOffset;
        archive.seek(offset + LFH_OFFSET_FOR_FILENAME_LENGTH);
        byte[] b = new byte[SHORT];
        archive.readFully(b);
        int fileNameLen = ZipShort.getValue(b);
        archive.readFully(b);
        int extraFieldLen = ZipShort.getValue(b);
        int lenToSkip = fileNameLen;
        while (lenToSkip > 0) {
            int skipped = archive.skipBytes(lenToSkip);
            if (skipped <= 0) {
                throw new RuntimeException("failed to skip file name in"
                                           + " local file header");
            }
            lenToSkip -= skipped;
        }
        byte[] localExtraData = new byte[extraFieldLen];
        archive.readFully(localExtraData);
        ze.setExtra(localExtraData);
        offsetEntry.dataOffset = offset + LFH_OFFSET_FOR_FILENAME_LENGTH
            + SHORT + SHORT + fileNameLen + extraFieldLen;

        if (entriesWithoutUTF8Flag.containsKey(ze)) {
            String orig = ze.getName();
            NameAndComment nc = entriesWithoutUTF8Flag.get(ze);
            ZipUtil.setNameAndCommentFromExtraFields(ze, nc.name,
                                                     nc.comment);
            if (!orig.equals(ze.getName())) {
                // Update entry with new name to avoid stale key issues
                // Remove old name mapping handled below by creating new map
            }
        }
        newEntries.put(ze, offsetEntry);
        newNameMap.put(ze.getName(), ze);
    }

    entries.clear();
    entries.putAll(newEntries);

    nameMap.clear();
    nameMap.putAll(newNameMap);
}
