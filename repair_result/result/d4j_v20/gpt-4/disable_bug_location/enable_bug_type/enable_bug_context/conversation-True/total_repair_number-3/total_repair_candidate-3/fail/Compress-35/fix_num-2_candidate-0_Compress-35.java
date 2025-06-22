public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;

    try {
        storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    } catch (IllegalArgumentException e) {
        storedSum = parseOctalOrBinary(header, CHKSUM_OFFSET, CHKSUMLEN);
    }

    long computedSum = computeCheckSum(header);

    return storedSum == computedSum;
}
