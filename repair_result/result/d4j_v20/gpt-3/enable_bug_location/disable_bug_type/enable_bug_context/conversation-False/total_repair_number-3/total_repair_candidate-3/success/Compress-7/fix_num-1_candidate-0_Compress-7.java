public static String parseName(byte[] buffer, final int offset, final int length) {
    try {
        int end = offset + length;
        int zeroIndex = offset;
        // Find zero byte to determine actual name length
        for (; zeroIndex < end; zeroIndex++) {
            if (buffer[zeroIndex] == 0) {
                break;
            }
        }
        // Create string using ISO-8859-1 to preserve byte values as characters directly
        return new String(buffer, offset, zeroIndex - offset, "ISO-8859-1");
    } catch (java.io.UnsupportedEncodingException e) {
        // Fallback to default charset in case ISO-8859-1 not supported, which is unlikely
        return new String(buffer, offset, length);
    }
}
