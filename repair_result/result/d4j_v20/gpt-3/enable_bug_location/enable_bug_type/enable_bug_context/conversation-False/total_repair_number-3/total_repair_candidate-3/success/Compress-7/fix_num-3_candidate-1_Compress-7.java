public static String parseName(byte[] buffer, final int offset, final int length) {
    StringBuffer result = new StringBuffer(length);
    int end = offset + length;

    // Ensure offset and length are within bounds
    if (offset < 0 || length < 0 || end > buffer.length) {
        throw new IllegalArgumentException("Offset or length is out of bounds");
    }

    for (int i = offset; i < end; ++i) {
        if (buffer[i] == 0) {
            break;
        }
        result.append((char) (buffer[i] & BYTE_MASK)); // Ensure proper byte to char conversion
    }

    return result.toString();
}
