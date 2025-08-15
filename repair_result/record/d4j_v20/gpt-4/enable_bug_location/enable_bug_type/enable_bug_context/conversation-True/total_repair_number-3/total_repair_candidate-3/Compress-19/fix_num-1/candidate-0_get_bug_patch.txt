public void reparseCentralDirectoryData(boolean hasUncompressedSize,
                                        boolean hasCompressedSize,
                                        boolean hasRelativeHeaderOffset,
                                        boolean hasDiskStart)
    throws ZipException {
    if (rawCentralDirectoryData != null) {
        // Instead of manually computing expected length, use addSizes to compute sizes length part
        int expectedLength = 0;
        if (hasUncompressedSize || hasCompressedSize) {
            // Prepare a temporary byte array to compute the size fields length
            byte[] temp = new byte[2 * DWORD]; // max two sizes
            expectedLength += addSizes(temp);
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
