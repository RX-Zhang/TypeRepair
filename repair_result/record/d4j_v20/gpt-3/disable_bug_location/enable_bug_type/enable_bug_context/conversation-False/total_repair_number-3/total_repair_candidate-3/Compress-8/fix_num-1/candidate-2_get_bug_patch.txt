public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    boolean sawDigits = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        // Trailing NUL or space indicate end of the octal number
        if (currentByte == 0 || currentByte == (byte) ' ') {
            // Once number parsing ended, ensure the rest are only NUL or spaces
            for (int j = i; j < end; j++) {
                final byte b = buffer[j];
                if (b != 0 && b != (byte) ' ') {
                    throw new IllegalArgumentException(
                        exceptionMessage(buffer, offset, length, j, b));
                }
            }
            break;
        }

        // Skip leading spaces and zeros as padding before digits start
        if (stillPadding) {
            if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
                continue;
            } else {
                stillPadding = false;
            }
        }

        // After padding, only digits '0' through '7' allowed
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        sawDigits = true;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    // If no digits were found, invalid input
    if (!sawDigits) {
        throw new IllegalArgumentException(
            exceptionMessage(buffer, offset, length, start, (byte) ' '));
    }

    return result;
}
