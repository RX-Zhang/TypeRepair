public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    // Check for all NULs - invalid input
    boolean allNuls = true;
    for (int i = start; i < end; i++) {
        if (buffer[i] != 0) {
            allNuls = false;
            break;
        }
    }
    if (allNuls) {
        throw new IllegalArgumentException("All bytes are NUL");
    }

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            // Trailing NUL terminates
            break;
        }
        // Skip leading spaces and zeros
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            // Trailer can be space, but space within digits is invalid
            if (currentByte == (byte) ' ') {
                break;
            }
        }
        stillPadding = false;

        // Check valid octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    // The last byte must be either NUL (0) or space
    byte lastByte = buffer[end - 1];
    if (lastByte != 0 && lastByte != (byte) ' ') {
        throw new IllegalArgumentException("String must be null or space terminated");
    }

    return result;
}
