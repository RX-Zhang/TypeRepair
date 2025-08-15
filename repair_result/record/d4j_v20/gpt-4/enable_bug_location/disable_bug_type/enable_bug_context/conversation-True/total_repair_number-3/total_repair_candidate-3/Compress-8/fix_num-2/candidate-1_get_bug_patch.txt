public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;
    boolean seenDigit = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            // All remaining bytes must be zero
            for (int j = i; j < end; j++) {
                if (buffer[j] != 0) {
                    throw new IllegalArgumentException(
                        exceptionMessage(buffer, offset, length, j, buffer[j]));
                }
            }
            break;
        }

        // Skip leading spaces and NULs
        if (stillPadding) {
            if (currentByte == (byte) ' ' || currentByte == 0) {
                continue;
            } else if (currentByte == (byte) '0') {
                stillPadding = false;
                seenDigit = true;
                result = 0;
                continue;
            } else if (currentByte >= '0' && currentByte <= '7') {
                stillPadding = false;
                seenDigit = true;
                result = currentByte - '0';
                continue;
            } else {
                throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
            }
        }

        // Trailing bytes must be space or NUL
        if (currentByte == (byte) ' ' || currentByte == 0) {
            // After digits end, ensure the rest are padding
            for (int j = i; j < end; j++) {
                if (buffer[j] != 0 && buffer[j] != (byte) ' ') {
                    throw new IllegalArgumentException(
                        exceptionMessage(buffer, offset, length, j, buffer[j]));
                }
            }
            break;
        }

        // Digit range check
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        seenDigit = true;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII octal digit
    }

    if (!seenDigit) {
        throw new IllegalArgumentException("No digits found in buffer");
    }

    return result;
}
