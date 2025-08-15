public void reparseCentralDirectoryData(boolean hasUncompressedSize,
                                        boolean hasCompressedSize,
                                        boolean hasRelativeHeaderOffset,
                                        boolean hasDiskStart)
    throws ZipException {
    if (rawCentralDirectoryData != null) {
        int expectedLength = 0;
        // According to ZIP64 spec, if one of size or compressed size is present,
        // both must be present. So, adjust logic accordingly.
        if (hasUncompressedSize || hasCompressedSize) {
            expectedLength += 2 * DWORD;
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
        // Ensure both size and compressedSize are handled together
        if (hasUncompressedSize || hasCompressedSize) {
            // If one is missing, treat missing as zero-length field (null)
            if (hasUncompressedSize) {
                size = new ZipEightByteInteger(rawCentralDirectoryData, offset);
            } else {
                size = null;
            }
            offset += DWORD;

            if (hasCompressedSize) {
                compressedSize = new ZipEightByteInteger(rawCentralDirectoryData,
                                                         offset);
            } else {
                compressedSize = null;
            }
            offset += DWORD;
        } else {
            size = null;
            compressedSize = null;
        }
        if (hasRelativeHeaderOffset) {
            relativeHeaderOffset =
                new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
        } else {
            relativeHeaderOffset = null;
        }
        if (hasDiskStart) {
            diskStart = new ZipLong(rawCentralDirectoryData, offset);
            offset += WORD;
        } else {
            diskStart = null;
        }
    }
}
