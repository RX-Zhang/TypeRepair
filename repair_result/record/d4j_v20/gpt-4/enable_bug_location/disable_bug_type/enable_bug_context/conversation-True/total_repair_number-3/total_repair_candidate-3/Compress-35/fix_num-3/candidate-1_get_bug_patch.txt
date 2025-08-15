public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;
    // Use TarUtils.parseOctal to parse the stored checksum in the header at the checksum offset.
    try {
        storedSum = TarUtils.parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    } catch (IllegalArgumentException e) {
        return false;
    }

    long computedSum = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (i >= CHKSUM_OFFSET && i < CHKSUM_OFFSET + CHKSUMLEN) {
            // Checksum field is treated as space characters (0x20) while computing the sum
            b = 0x20;
        }
        computedSum += (b & 0xff);
    }

    return storedSum == computedSum;
}
