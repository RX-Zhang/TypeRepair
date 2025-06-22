public static boolean verifyCheckSum(byte[] header) {
    long storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    long computedSum = computeCheckSum(header);

    // The original checking in TarArchiveInputStream treats the checksum field
    // as if it were all spaces (0x20) for the calculation,
    // so we compare storedSum to computedSum as is.
    return storedSum == computedSum;
}
