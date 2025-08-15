public static boolean verifyCheckSum(byte[] header) {
    long storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    long computedSum = computeCheckSum(header);

    // According to the tar spec, the checksum field is treated as if it were all spaces when computing the checksum
    // So we replace those bytes with space and recompute the checksum
    byte[] headerForChecksum = header.clone();
    for (int i = CHKSUM_OFFSET; i < CHKSUM_OFFSET + CHKSUMLEN; i++) {
        headerForChecksum[i] = ' ';
    }
    long unsignedSum = computeCheckSum(headerForChecksum);

    return storedSum == unsignedSum || storedSum == computedSum;
}
