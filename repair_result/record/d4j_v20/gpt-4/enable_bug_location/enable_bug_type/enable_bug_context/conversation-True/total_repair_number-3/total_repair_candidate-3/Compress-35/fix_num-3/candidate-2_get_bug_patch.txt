public static boolean verifyCheckSum(byte[] header) {
    long storedSum = TarUtils.parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    long computedSum = TarUtils.computeCheckSum(header);

    // According to the tar specification, the checksum is computed with the checksum bytes set to space (0x20)
    // so we need to compute a sum with those bytes replaced by spaces:
    long checkSumWithSpaces = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (CHKSUM_OFFSET <= i && i < CHKSUM_OFFSET + CHKSUMLEN) {
            checkSumWithSpaces += 0x20;
        } else {
            checkSumWithSpaces += BYTE_MASK & b;
        }
    }

    return storedSum == computedSum || storedSum == checkSumWithSpaces;
}
