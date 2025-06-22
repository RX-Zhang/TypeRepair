public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException(
                "Length must be at least 2 bytes for parseOctal");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;
    
    // Check that the entire buffer isn't just spaces or NULs
    boolean allNullsOrSpaces = true;
    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte != 0 && currentByte != (byte) ' ') {
            allNullsOrSpaces = false;
            break;
        }
    }
    if (allNullsOrSpaces) {
        throw new IllegalArgumentException(
                "Buffer cannot be all NULs or spaces");
    }

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            // Must be trailing NUL or end of field
            break;
        }
        // Skip leading spaces and '0's padding
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                // Trailing space, end of number
                break;
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
