public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long    result = 0;
    boolean stillPadding = true;
    int     end = offset + length;
    int     start = offset;
    boolean seenDigit = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        // If we hit a null byte, ensure all remaining bytes are also NUL (0)
        if (currentByte == 0) {
            // Check remaining bytes are all NULs
            for (int j = i; j < end; j++) {
                if (buffer[j] != 0) {
                    throw new IllegalArgumentException(
                        exceptionMessage(buffer, offset, length, j, buffer[j]));
                }
            }
            break;
        }

        // Skip leading spaces
        if (stillPadding && currentByte == (byte) ' ') {
            continue;
        }

        if (stillPadding && currentByte == (byte) '0') {
            stillPadding = false;
            seenDigit = true;
            result = 0;
            continue;
        }

        if (!stillPadding && currentByte == (byte) ' ') {
            // trailing space found, all remaining bytes must be null or space
            for (int j = i + 1; j < end; j++) {
                if (buffer[j] != 0 && buffer[j] != (byte) ' ') {
                    throw new IllegalArgumentException(
                        exceptionMessage(buffer, offset, length, j, buffer[j]));
                }
            }
            break;
        }

        // Must be octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        stillPadding = false;
        seenDigit = true;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    if (!seenDigit) {
        throw new IllegalArgumentException("No octal digits found");
    }

    return result;
}
