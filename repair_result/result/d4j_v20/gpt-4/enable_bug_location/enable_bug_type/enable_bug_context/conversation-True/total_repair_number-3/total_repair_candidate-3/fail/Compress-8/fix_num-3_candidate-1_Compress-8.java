public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Should be at least 2 bytes long");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;

    // Check if all bytes are NUL (0)
    boolean allNuls = true;
    for (int i = offset; i < end; i++) {
        if (buffer[i] != 0) {
            allNuls = false;
            break;
        }
    }
    if (allNuls) {
        throw new IllegalArgumentException("All bytes are NUL");
    }

    int start = offset;
    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces and zeros
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
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    // Ensure last byte is NUL or space (trailer)
    byte lastByte = buffer[end - 1];
    if (lastByte != 0 && lastByte != (byte) ' ') {
        throw new IllegalArgumentException("No trailing NUL or space");
    }

    return result;
}
