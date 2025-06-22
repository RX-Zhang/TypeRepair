public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int firstDigit = -1;

    for (int i = offset; i < end; i++) {
        final byte currentByte = buffer[i];

        // Trailing NUL marks end of the octal string
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces or '0's as padding
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                break;
            }
        }

        stillPadding = false;

        // Validate octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0');

        if (firstDigit == -1) {
            firstDigit = i;
        }
    }

    if (stillPadding) {
        // All were spaces or zeros, invalid input
        throw new IllegalArgumentException("No valid octal digits");
    }

    // Check that last byte is a space or NUL (trailer)
    byte lastByte = buffer[offset + length - 1];
    if (lastByte != 0 && lastByte != (byte) ' ') {
        throw new IllegalArgumentException("Invalid octal trailer, must be NUL or space");
    }

    return result;
}
