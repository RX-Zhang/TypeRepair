public static boolean verifyCheckSum(byte[] header) {
    long storedSum = parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);
    long computedSum = 0;

    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (i >= CHKSUM_OFFSET && i < CHKSUM_OFFSET + CHKSUMLEN) {
            b = ' ';
        }
        computedSum += 0xFF & b;
    }

    return storedSum == computedSum;
}
