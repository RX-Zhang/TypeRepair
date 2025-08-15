public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;
    long unsignedSum = 0;
    long signedSum = 0;

    int digits = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (CHKSUM_OFFSET <= i && i < CHKSUM_OFFSET + CHKSUMLEN) {
            // According to TAR format, checksum field is 8 bytes:
            // The first 6 bytes are octal digits, followed by a NUL and a space.
            if ('0' <= b && b <= '7' && digits < 6) {
                storedSum = storedSum * 8 + (b - '0');
                digits++;
            } else if (digits > 0) {
                digits = 6;
            }
            b = 0x20; // ASCII space (0x20), checksum field is treated as spaces in sum calculation
        }
        unsignedSum += (b & 0xff);
        signedSum += b;
    }
    return storedSum == unsignedSum || storedSum == signedSum;
}
