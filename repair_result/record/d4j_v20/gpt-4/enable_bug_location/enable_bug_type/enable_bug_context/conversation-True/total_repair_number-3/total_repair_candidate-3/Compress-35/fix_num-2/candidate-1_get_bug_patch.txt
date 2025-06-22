public static boolean verifyCheckSum(byte[] header) {
    long storedSum = 0;
    int digits = 0;

    for (int i = CHKSUM_OFFSET; i < CHKSUM_OFFSET + CHKSUMLEN; i++) {
        byte b = header[i];
        if (' ' == b || b == 0) {
            continue;
        }
        if (b >= '0' && b <= '7') {
            storedSum = (storedSum << 3) + (b - '0');
            digits++;
        } else {
            break;
        }
    }
    if (digits == 0) {
        return false;
    }

    long computedSum = computeCheckSum(header);
    return storedSum == computedSum;
}
