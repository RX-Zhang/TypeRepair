public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Invalid length: should be at least 2 bytes long");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    // Check if all bytes are NULs (0)
    boolean allNuls = true;
    for (int i = start; i < end; i++) {
        if (buffer[i] != 0) {
            allNuls = false;
            break;
        }
    }
    if (allNuls) {
        throw new IllegalArgumentException("Invalid input: all bytes are NULs");
    }

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces and zeroes
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                break;
            }
        } else if (currentByte == (byte) '\0') {
            // Embedded NUL is invalid
            throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
        } else if (currentByte == (byte) ' ') {
            break;
        }

        stillPadding = false;

        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    // Additional check: last byte must be NUL or space
    byte lastByte = buffer[end - 1];
    if (lastByte != 0 && lastByte != (byte) ' ') {
        throw new IllegalArgumentException("Invalid input: last byte must be NUL or space");
    }

    return result;
}
