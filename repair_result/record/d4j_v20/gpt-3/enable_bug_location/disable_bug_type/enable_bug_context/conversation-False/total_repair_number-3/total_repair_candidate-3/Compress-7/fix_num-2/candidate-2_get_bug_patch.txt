public static String parseName(byte[] buffer, final int offset, final int length) {
    int end = offset + length;
    int zeroIndex = offset;

    for (; zeroIndex < end; ++zeroIndex) {
        if (buffer[zeroIndex] == 0) {
            break;
        }
    }

    return new String(buffer, offset, zeroIndex - offset, java.nio.charset.StandardCharsets.ISO_8859_1);
}
