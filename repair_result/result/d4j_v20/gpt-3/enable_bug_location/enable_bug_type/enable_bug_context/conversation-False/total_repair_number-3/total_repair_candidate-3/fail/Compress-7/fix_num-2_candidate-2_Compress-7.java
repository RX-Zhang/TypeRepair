public static String parseName(byte[] buffer, final int offset, final int length) {
    if (buffer == null) {
        throw new IllegalArgumentException("Buffer cannot be null");
    }
    if (offset < 0 || length < 0 || offset + length > buffer.length) {
        throw new IndexOutOfBoundsException("Offset or length is out of bounds");
    }

    StringBuffer result = new StringBuffer(length);
    int end = offset + length;

    for (int i = offset; i < end; ++i) {
        if (buffer[i] == 0) {
            break;
        }
        result.append((char) buffer[i]);
    }

    return result.toString();
}
