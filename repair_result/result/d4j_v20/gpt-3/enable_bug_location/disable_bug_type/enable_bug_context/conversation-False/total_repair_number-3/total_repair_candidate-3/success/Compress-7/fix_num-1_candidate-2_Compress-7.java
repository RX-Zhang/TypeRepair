public static String parseName(byte[] buffer, final int offset, final int length) {
    return new String(buffer, offset, length, java.nio.charset.StandardCharsets.ISO_8859_1).split("\0", 2)[0];
}
