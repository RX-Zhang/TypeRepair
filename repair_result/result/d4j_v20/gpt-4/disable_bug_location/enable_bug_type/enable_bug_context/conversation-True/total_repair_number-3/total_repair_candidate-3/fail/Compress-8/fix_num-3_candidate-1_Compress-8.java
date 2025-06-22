public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    for (int i = start; i < end; i++){
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces and zeros as padding
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                break;
            }
        }

        stillPadding = false;

        // Check if byte is octal digit
        if (currentByte < (byte) '0' || currentByte > (byte) '7'){
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    // Trailing byte must be NUL or space if we ended early
    if (end > offset && buffer[end - 1] != 0 && buffer[end - 1] != (byte) ' ') {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end - 1, buffer[end - 1]));
    }

    return result;
}
