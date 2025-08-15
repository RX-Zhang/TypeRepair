public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;
    long unsignedSum = 0;
    long signedSum = 0;

    int digits = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (CHKSUM_OFFSET <= i && i < CHKSUM_OFFSET + CHKSUMLEN) {
            // As per tar spec, checksum field is 8 bytes: 6 digits, 1 space, 1 NUL
            // Only first 6 digits (octal) are part of the stored checksum
            if ('0' <= b && b <= '7' && digits < 6) {
                storedSum = storedSum * 8 + (b - '0');
                digits++;
            } else if (b == 0 || b == ' ') {
                // ignore trailing NUL or space in checksum field
            } else {
                // If invalid char found after digits, ignore bytes but do not increment digits
            }
            // When calculating the sums, the checksum field bytes are treated as spaces (0x20)
            b = ' ';
        }
        unsignedSum += 0xff & b;
        signedSum += b;
    }

    return storedSum == unsignedSum || storedSum == signedSum;
}
