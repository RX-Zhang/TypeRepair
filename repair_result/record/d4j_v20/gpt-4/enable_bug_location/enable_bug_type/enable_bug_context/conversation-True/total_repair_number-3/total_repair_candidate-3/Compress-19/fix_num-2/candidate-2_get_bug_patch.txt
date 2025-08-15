public void reparseCentralDirectoryData(boolean hasUncompressedSize,
                                        boolean hasCompressedSize,
                                        boolean hasRelativeHeaderOffset,
                                        boolean hasDiskStart)
    throws ZipException {
    if (rawCentralDirectoryData != null) {
        int expectedLength = 0;
        if (hasUncompressedSize) {
            expectedLength += DWORD;
        }
        if (hasCompressedSize) {
            expectedLength += DWORD;
        }
        if (hasRelativeHeaderOffset) {
            expectedLength += DWORD;
        }
        if (hasDiskStart) {
            expectedLength += WORD;
        }

        // Adjust expected length according to actual non-null fields
        int actualLength = 0;
        if (size != null) {
            actualLength += DWORD;
        }
        if (compressedSize != null) {
            actualLength += DWORD;
        }
        if (relativeHeaderOffset != null) {
            actualLength += DWORD;
        }
        if (diskStart != null) {
            actualLength += WORD;
        }

        if (rawCentralDirectoryData.length < expectedLength) {
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
            compressedSize = new ZipEightByteInteger(rawCentralDirectoryData,
                                                     offset);
            offset += DWORD;
        }
        if (hasRelativeHeaderOffset) {
            relativeHeaderOffset =
                new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
        }
        if (hasDiskStart) {
            diskStart = new ZipLong(rawCentralDirectoryData, offset);
            offset += WORD;
        }
    }
}
