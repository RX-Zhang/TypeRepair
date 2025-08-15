public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;
    long unsignedSum = 0;
    long signedSum = 0;

    int digits = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (CHKSUM_OFFSET <= i && i < CHKSUM_OFFSET + CHKSUMLEN) {
            if ('0' <= b && b <= '7' && digits < 6) {
                storedSum = storedSum * 8 + (b - '0');
                digits++;  // Moved digits increment here to ensure it only increments for valid characters
            } else if (digits > 0) {
                digits = 6;  // Ensure we stop counting after 6 valid digits
            }
            b = 0;  // Set b to 0 instead of space, as we want to ignore this byte in sums
        }
        unsignedSum += 0xff & b;
        signedSum += b;
    }
    return storedSum == unsignedSum || storedSum == signedSum;
}
