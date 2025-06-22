public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;

    try {
        storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    } catch (IllegalArgumentException ex) {
        return false;
    }

    long calculatedSum = computeCheckSum(header);

    return storedSum == calculatedSum;
}
