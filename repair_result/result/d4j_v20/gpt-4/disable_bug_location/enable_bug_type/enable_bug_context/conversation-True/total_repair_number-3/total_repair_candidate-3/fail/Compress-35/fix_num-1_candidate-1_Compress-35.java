public static boolean verifyCheckSum(byte[] header) {
    long storedSum = parseOctalOrBinary(header, CHKSUM_OFFSET, CHKSUMLEN);
    long computedSum = computeCheckSum(header);

    // Checksum field is treated as spaces during calculation
    // so adjust computedSum if necessary (not shown here, 
    // assuming computeCheckSum already accounts for that)
    return storedSum == computedSum;
}
