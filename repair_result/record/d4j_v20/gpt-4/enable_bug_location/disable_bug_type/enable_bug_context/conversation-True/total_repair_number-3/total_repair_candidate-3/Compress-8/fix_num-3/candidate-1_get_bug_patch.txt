public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    // Check for all NULs (if so, throw)
    boolean allNuls = true;
    for (int i = start; i < end; i++) {
        if (buffer[i] != 0) {
            allNuls = false;
            break;
        }
    }
    if (allNuls) {
        throw new IllegalArgumentException("Buffer is all NULs");
    }

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces and leading zeroes
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                break;
            }
        }

        stillPadding = false;

        // Check the valid octal digit range
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0');
    }

    // Ensure that the last byte of the field is either NUL or space
    byte lastByte = buffer[end - 1];
    if (lastByte != 0 && lastByte != (byte) ' ') {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end - 1, lastByte));
    }

    return result;
}
