public void reparseCentralDirectoryData(boolean hasUncompressedSize,
                                        boolean hasCompressedSize,
                                        boolean hasRelativeHeaderOffset,
                                        boolean hasDiskStart)
    throws ZipException {
    if (rawCentralDirectoryData != null) {
        int expectedLength = 0;
        // Adjust expected length calculation: uncompressed and compressed sizes must both be present or both absent
        if (hasUncompressedSize || hasCompressedSize) {
            if (!(hasUncompressedSize && hasCompressedSize)) {
                throw new ZipException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
            }
            expectedLength += DWORD + DWORD;
        }
        if (hasRelativeHeaderOffset) {
            expectedLength += DWORD;
        }
        if (hasDiskStart) {
            expectedLength += WORD;
        }

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
            size = new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
        }
        if (hasCompressedSize) {
            compressedSize = new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
        }
        if (hasRelativeHeaderOffset) {
            relativeHeaderOffset = new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
        }
        if (hasDiskStart) {
            diskStart = new ZipLong(rawCentralDirectoryData, offset);
            offset += WORD;
        }
    }
}
