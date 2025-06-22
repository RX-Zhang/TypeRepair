public void reparseCentralDirectoryData(boolean hasUncompressedSize,
                                        boolean hasCompressedSize,
                                        boolean hasRelativeHeaderOffset,
                                        boolean hasDiskStart)
    throws ZipException {
    if (rawCentralDirectoryData != null) {
        int actualLength = rawCentralDirectoryData.length;
        int minExpectedLength = 2 * DWORD; // size + compressedSize must be present

        // The central directory entry can have additional fields like relativeHeaderOffset and diskStart
        int maxExpectedLength = 2 * DWORD  // size + compressedSize
            + (hasRelativeHeaderOffset ? DWORD : 0)
            + (hasDiskStart ? WORD : 0);

        // Validate minimum requirements: size and compressedSize must both be present
        if (!hasUncompressedSize || !hasCompressedSize) {
            throw new ZipException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
        }

        if (actualLength < minExpectedLength || actualLength > maxExpectedLength) {
            throw new ZipException("central directory zip64 extended"
                                   + " information extra field's length"
                                   + " doesn't match central directory"
                                   + " data.  Expected length between "
                                   + minExpectedLength + " and "
                                   + maxExpectedLength + " but is "
                                   + actualLength);
        }

        int offset = 0;
        if (hasUncompressedSize) {
            size = new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
        }
        if (hasCompressedSize) {
            compressedSize = new ZipEightByteInteger(rawCentralDirectoryData,
                                                     offset);
            offset += DWORD;
        }
        if (hasRelativeHeaderOffset && offset + DWORD <= actualLength) {
            relativeHeaderOffset =
                new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
        } else {
            relativeHeaderOffset = null;
        }
        if (hasDiskStart && offset + WORD <= actualLength) {
            diskStart = new ZipLong(rawCentralDirectoryData, offset);
            offset += WORD;
        } else {
            diskStart = null;
        }
    }
}
