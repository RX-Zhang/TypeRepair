public static boolean verifyCheckSum(byte[] header) {
    long storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    long computedSum = computeCheckSum(header);

    // When verifying the checksum, the checksum field is treated as spaces (0x20)
    // so compute again with spaces in the checksum area
    long signedSum = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (CHKSUM_OFFSET <= i && i < CHKSUM_OFFSET + CHKSUMLEN) {
            b = ' ';
        }
        signedSum += b;
    }

    return storedSum == computedSum || storedSum == signedSum;
}
