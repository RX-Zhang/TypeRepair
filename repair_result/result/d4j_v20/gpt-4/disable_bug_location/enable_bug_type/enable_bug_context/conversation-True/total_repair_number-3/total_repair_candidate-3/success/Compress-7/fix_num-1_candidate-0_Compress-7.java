public static String parseName(byte[] buffer, final int offset, final int length) {
    StringBuffer result = new StringBuffer(length);
    int          end = offset + length;

    for (int i = offset; i < end; ++i) {
        if (buffer[i] == 0) {
            break;
        }
        // Mask with BYTE_MASK to correctly convert to unsigned byte before casting
        result.append((char) (buffer[i] & 0xFF));
    }

    return result.toString();
}
