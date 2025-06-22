public static String parseName(byte[] buffer, final int offset, final int length) {
    StringBuilder result = new StringBuilder(length);
    int end = offset + length;

    for (int i = offset; i < end; ++i) {
        if (buffer[i] == 0) {
            break;
        }
        int b = buffer[i] & 0xFF;
        result.append((char) b);
    }

    return result.toString();
}
