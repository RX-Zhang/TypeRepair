public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;

    // The CHKSUM field is 8 bytes long in tar headers
    // parseOctal can be used to read the stored checksum value
    storedSum = TarUtils.parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);

    // Compute the checksum of the header with the checksum field treated as spaces
    long computedSum = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (CHKSUM_OFFSET <= i && i < CHKSUM_OFFSET + CHKSUMLEN) {
            // The checksum field is treated as space characters (0x20)
            b = 0x20;
        }
        computedSum += (b & 0xff);
    }

    return storedSum == computedSum;
}
