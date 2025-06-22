public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Octal length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    // Check that all bytes are either 0, space, or octal digits, and that
    // the encoding ends with NUL or space
    // Also check that the field does not contain embedded spaces or NULs except trailing

    boolean foundNonPadding = false;
    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0 || currentByte == (byte) ' ') {
            if (!foundNonPadding) {
                // leading padding, continue
                continue;
            } else {
                // trailing padding or terminator, break loop
                break;
            }
        }
        // If we find a byte that is not '0'-'7', space or NUL, invalid
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }
        foundNonPadding = true;
    }

    // parse logic
    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces
        if (currentByte == (byte) ' ') {
            if (stillPadding) {
                continue;
            }
            break;
        }
        if (currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
        }

        stillPadding = false;

        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
