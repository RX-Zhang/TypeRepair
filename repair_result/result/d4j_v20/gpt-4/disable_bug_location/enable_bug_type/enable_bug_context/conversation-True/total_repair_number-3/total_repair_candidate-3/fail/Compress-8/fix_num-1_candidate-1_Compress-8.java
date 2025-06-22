public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        
        // Trailing NUL or space indicates end of the octal number
        if (currentByte == 0 || currentByte == (byte) ' ') {
            break;
        }

        // Skip leading spaces and leading zeroes
        if (stillPadding) {
            if (currentByte == (byte) ' ') {
                continue;
            }
            if (currentByte == (byte) '0') {
                continue;
            }
            stillPadding = false;
        }

        // After padding skipped, check bytes for valid octal digits (0-7)
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII octal digit to number
    }

    return result;
}
