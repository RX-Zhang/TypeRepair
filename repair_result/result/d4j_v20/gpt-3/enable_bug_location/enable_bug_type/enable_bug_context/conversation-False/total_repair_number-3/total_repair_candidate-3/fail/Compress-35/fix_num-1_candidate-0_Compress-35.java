public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;
    long unsignedSum = 0;
    long signedSum = 0;

    int digits = 0;
    for (int i = 0; i < header.length; i++) {
        byte b = header[i];
        if (CHKSUM_OFFSET <= i && i < CHKSUM_OFFSET + CHKSUMLEN) {
            if ('0' <= b && b <= '7' && digits < 6) { // Changed digits++ < 6 to digits < 6
                storedSum = storedSum * 8 + (b - '0'); // Ensure correct conversion
                digits++; // Increment digits after using it
            } else if (digits > 0) {
                digits = 6; // Ensure we stop counting digits after the first invalid character
            }
            b = ' '; // Set b to space after processing checksum digits
        }
        unsignedSum += 0xff & b; // Accumulate unsigned sum
        signedSum += b; // Accumulate signed sum
    }
    return storedSum == unsignedSum || storedSum == signedSum; // Return true if checksums match
}
