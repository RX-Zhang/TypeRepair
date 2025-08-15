public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;
    try {
        storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    } catch (IllegalArgumentException e) {
        return false;
    }
    long computedSum = computeCheckSum(header);

    for (int i = CHKSUM_OFFSET; i < CHKSUM_OFFSET + CHKSUMLEN; i++) {
        computedSum -= BYTE_MASK & header[i];
        computedSum += ' ';
    }

    return storedSum == computedSum;
}
