public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    boolean seenDigit = false;
    int end = offset + length;

    for (int i = offset; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            // NUL byte, end of value
            break;
        }

        if (currentByte == (byte) ' ') {
            if (stillPadding) {
                // Skip leading spaces
                continue;
            }
            // Spaces after digits mean end of number
            break;
        }

        if (currentByte < (byte) '0' || currentByte > (byte) '7') {
            throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
        }

        stillPadding = false;
        seenDigit = true;
        result = (result << 3) + (currentByte - '0');
    }

    // If no digits seen (all NULs or spaces), error
    if (!seenDigit) {
        throw new IllegalArgumentException("No digits found in octal");
    }

    return result;
}
