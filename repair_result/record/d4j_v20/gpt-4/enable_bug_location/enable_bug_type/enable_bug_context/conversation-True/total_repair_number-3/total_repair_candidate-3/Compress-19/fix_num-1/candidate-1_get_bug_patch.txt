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

        // Fix: handle the case when rawCentralDirectoryData length can be greater than expectedLength,
        // so accept lengths that are >= expectedLength instead of strictly equal.
        // According to the test failure, sometimes there is excess data in the extra field.
        if (rawCentralDirectoryData.length < expectedLength) {
            throw new ZipException("central directory zip64 extended" +
                                   " information extra field's length" +
                                   " doesn't match central directory" +
                                   " data.  Expected length at least " +
                                   expectedLength + " but is " +
                                   rawCentralDirectoryData.length);
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
