public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;
    long unsignedSum = 0;
    long signedSum = 0;

    int digits = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (CHKSUM_OFFSET <= i && i < CHKSUM_OFFSET + CHKSUMLEN) {
            // Only parse up to 6 octal digits for storedSum
            if ('0' <= b && b <= '7' && digits < 6) {
                storedSum = storedSum * 8 + (b - '0');
                digits++;
            } else if (digits > 0) {
                digits = 6; // stop parsing digits once non-octal or after 6 digits
            }
            b = ' ';
        }
        unsignedSum += 0xff & b;
        signedSum += b;
    }
    return storedSum == unsignedSum || storedSum == signedSum;
}
