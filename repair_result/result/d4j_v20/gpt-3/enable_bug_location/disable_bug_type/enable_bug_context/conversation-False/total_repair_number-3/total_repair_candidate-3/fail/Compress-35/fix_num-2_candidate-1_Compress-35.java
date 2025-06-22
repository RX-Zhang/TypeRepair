public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;
    long unsignedSum = 0;
    long signedSum = 0;

    int digits = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (CHKSUM_OFFSET <= i && i < CHKSUM_OFFSET + CHKSUMLEN) {
            // The checksum field is traditionally 8 bytes: 6 digits, a null and a space
            if ('0' <= b && b <= '7' && digits < 6) {
                storedSum = storedSum * 8 + (b - '0');
                digits++;
            }
            // ignore the rest characters in the checksum field (null, space etc.)
            b = ' ';
        }
        unsignedSum += (0xff & b);
        signedSum += b;
    }
    return storedSum == unsignedSum || storedSum == signedSum;
}
