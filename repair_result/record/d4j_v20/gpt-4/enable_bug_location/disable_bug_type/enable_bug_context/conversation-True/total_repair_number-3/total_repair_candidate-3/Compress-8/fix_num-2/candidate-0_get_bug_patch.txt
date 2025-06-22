public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;

    // Check for all NULs - not allowed
    boolean allNUL = true;
    for (int i = offset; i < end; i++) {
        if (buffer[i] != 0) {
            allNUL = false;
            break;
        }
    }
    if (allNUL) {
        throw new IllegalArgumentException("Input buffer is all NULs");
    }

    int start = offset;
    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            // trailing NUL means end of octal string
            break;
        }

        // Skip leading spaces and zeros
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                // Trailing space means end of octal string
                break;
            }
        }

        stillPadding = false;

        // Check for invalid characters (must be between '0' and '7')
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
