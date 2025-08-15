public void reparseCentralDirectoryData(boolean hasUncompressedSize,
                                        boolean hasCompressedSize,
                                        boolean hasRelativeHeaderOffset,
                                        boolean hasDiskStart)
    throws ZipException {
    if (rawCentralDirectoryData != null) {
        int expectedLength = 0;
        if (hasUncompressedSize && hasCompressedSize) {
            expectedLength += DWORD * 2;
        } else if (hasUncompressedSize || hasCompressedSize) {
            // According to LFH_MUST_HAVE_BOTH_SIZES_MSG, both sizes must be present or neither.
            throw new ZipException("Local File Header must have both uncompressed size and compressed size");
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
        if (hasUncompressedSize && hasCompressedSize) {
            size = new ZipEightByteInteger(rawCentralDirectoryData, offset);
            offset += DWORD;
            compressedSize = new ZipEightByteInteger(rawCentralDirectoryData,
                                                     offset);
            offset += DWORD;
        }
        // If only one size is present, this is an error and should have thrown above.
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
