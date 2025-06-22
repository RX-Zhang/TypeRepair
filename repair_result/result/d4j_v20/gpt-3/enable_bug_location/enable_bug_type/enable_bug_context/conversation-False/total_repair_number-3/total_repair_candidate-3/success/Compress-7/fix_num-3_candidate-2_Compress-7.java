public static String parseName(byte[] buffer, final int offset, final int length) {
    StringBuffer result = new StringBuffer(length);
    int end = offset + length;

    // Ensure we do not exceed buffer length
    if (offset < 0 || length < 0 || end > buffer.length) {
        throw new IllegalArgumentException("Invalid offset or length");
    }

    for (int i = offset; i < end; ++i) {
        if (buffer[i] == 0) {
            break;
        }
        result.append((char) (buffer[i] & BYTE_MASK)); // Use BYTE_MASK to handle byte conversion properly
    }

    return result.toString();
}
