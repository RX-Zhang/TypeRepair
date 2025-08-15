public static boolean verifyCheckSum(byte[] header) {
    long storedSum;
    try {
        storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    } catch (IllegalArgumentException e) {
        return false;
    }
    long checkSum = computeCheckSum(header);
    return storedSum == checkSum;
}
