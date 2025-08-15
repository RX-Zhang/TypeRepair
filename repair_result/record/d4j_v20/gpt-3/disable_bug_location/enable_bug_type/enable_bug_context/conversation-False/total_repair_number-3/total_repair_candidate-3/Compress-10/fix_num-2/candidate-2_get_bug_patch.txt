private void resolveLocalFileHeaderData(Map<ZipArchiveEntry, NameAndComment>
                                        entriesWithoutUTF8Flag)
    throws IOException {
    // changing the name of a ZipArchiveEntry is going to change
    // the hashcode - see COMPRESS-164
    // Map needs to be reconstructed in order to keep central
    // directory order
    for (ZipArchiveEntry ze : entries.keySet()) {
        OffsetEntry offsetEntry = entries.get(ze);
        long offset = offsetEntry.headerOffset;
        archive.seek(offset + LFH_OFFSET_FOR_FILENAME_LENGTH);
        byte[] b = new byte[SHORT];
        archive.readFully(b);
        int fileNameLen = ZipShort.getValue(b);
        archive.readFully(b);
        int extraFieldLen = ZipShort.getValue(b);

        // Read file name bytes (needed if we want to read the extra data correctly)
        byte[] fileNameData = new byte[fileNameLen];
        archive.readFully(fileNameData);

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
                nameMap.remove(orig);
                nameMap.put(ze.getName(), ze);
            }
        }
    }
}
