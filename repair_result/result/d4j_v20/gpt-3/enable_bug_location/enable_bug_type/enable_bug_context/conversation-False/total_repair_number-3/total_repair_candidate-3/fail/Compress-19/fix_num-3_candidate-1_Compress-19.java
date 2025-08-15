public void reparseCentralDirectoryData(boolean hasUncompressedSize,
                                        boolean hasCompressedSize,
                                        boolean hasRelativeHeaderOffset,
                                        boolean hasDiskStart)
    throws ZipException {
    if (rawCentralDirectoryData != null) {
        int expectedLength = (hasUncompressedSize ? DWORD : 0)
            + (hasCompressedSize ? DWORD : 0)
            + (hasRelativeHeaderOffset ? DWORD : 0)
            + (hasDiskStart ? WORD : 0);
        if (rawCentralDirectoryData.length != expectedLength) {
            throw new ZipException("central directory zip64 extended"
                                   + " information extra field's length"
                                   + " doesn't match central directory"
                                   + " data.  Expected length "
                                   + expectedLength + " but is "
                                   + rawCentralDirectoryData.length);
        }
        int offset = 0;
        if (hasUncompressedSize) {
            if (offset + DWORD > rawCentralDirectoryData.length) {
                throw new ZipException("Insufficient data for uncompressed size.");
            }
            size = new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
        }
        if (hasCompressedSize) {
            if (offset + DWORD > rawCentralDirectoryData.length) {
                throw new ZipException("Insufficient data for compressed size.");
            }
            compressedSize = new ZipEightByteInteger(rawCentralDirectoryData,
                                                     offset);
            offset += DWORD;
        }
        if (hasRelativeHeaderOffset) {
            if (offset + DWORD > rawCentralDirectoryData.length) {
                throw new ZipException("Insufficient data for relative header offset.");
            }
            relativeHeaderOffset =
                new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
        }
        if (hasDiskStart) {
            if (offset + WORD > rawCentralDirectoryData.length) {
                throw new ZipException("Insufficient data for disk start.");
            }
            diskStart = new ZipLong(rawCentralDirectoryData, offset);
            offset += WORD;
        }
    }
}
