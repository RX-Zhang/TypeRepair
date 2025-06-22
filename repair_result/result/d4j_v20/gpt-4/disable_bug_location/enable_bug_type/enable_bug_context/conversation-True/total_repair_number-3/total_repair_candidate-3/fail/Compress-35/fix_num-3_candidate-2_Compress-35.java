public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;

    // Use TarUtils' parseOctal method to correctly parse the stored checksum from the header
    storedSum = TarUtils.parseOctal(header, CHKSUM_OFFSET, CHKSUMLEN);

    // Use TarUtils' computeCheckSum to calculate the checksum of the header
    long actualSum = TarUtils.computeCheckSum(header);

    return storedSum == actualSum;
}
