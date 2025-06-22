public static boolean verifyCheckSum(byte[] header) {
    long storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    long computedSum = computeCheckSum(header);

    // The checksum field is treated as if it were filled with spaces for checksum calculation
    return storedSum == computedSum;
}
