public static String parseName(byte[] buffer, final int offset, final int length) {
    if (buffer == null || offset < 0 || length < 0 || offset + length > buffer.length) {
        throw new IllegalArgumentException("Invalid buffer, offset, or length");
    }

    StringBuffer result = new StringBuffer(length);
    int end = offset + length;

    for (int i = offset; i < end; ++i) {
        if (buffer[i] == 0) {
            break;
        }
        result.append((char) (buffer[i] & BYTE_MASK)); // Ensure byte is treated correctly
    }

    return result.toString();
}
