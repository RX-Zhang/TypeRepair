public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;

    try {
        storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    } catch (IllegalArgumentException ignored) {
        return false;
    }

    long computedSum = computeCheckSum(header);

    return storedSum == computedSum;
}
