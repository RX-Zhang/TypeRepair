public static boolean verifyCheckSum(byte[] header) {
    long storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    long computedSum = computeCheckSum(header);

    // The checksum field is treated as if it were filled with spaces (0x20)
    for (int i = CHKSUM_OFFSET; i < CHKSUM_OFFSET + CHKSUMLEN; i++) {
        computedSum -= BYTE_MASK & header[i];
        computedSum += ' ';
    }

    return storedSum == computedSum;
}
