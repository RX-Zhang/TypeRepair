public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int i = offset;

    for (; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            // Valid trailing NUL, number ended
            break;
        }
        // Skip leading spaces and zeros
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                // Trailing space, number ended
                break;
            }
        }

        stillPadding = false;

        // Check for valid octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0');
    }

    // After parsing digits, all remaining bytes must be NUL or spaces
    for (; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte != 0 && currentByte != (byte) ' ') {
            throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
        }
    }

    return result;
}
