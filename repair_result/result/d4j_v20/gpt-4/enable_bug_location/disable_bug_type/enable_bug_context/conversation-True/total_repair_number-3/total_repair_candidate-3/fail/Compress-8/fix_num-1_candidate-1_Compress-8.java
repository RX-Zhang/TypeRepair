public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    // Check that all bytes are either NUL(0) or space, except for at least one byte that is not
    boolean allNulls = true;
    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte != 0) {
            allNulls = false;
            break;
        }
    }
    if (allNulls) {
        throw new IllegalArgumentException("Input is all NUL bytes");
    }

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces and zeros
        if (stillPadding) {
            if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
                continue;
            } else {
                stillPadding = false;
            }
        }

        // If trailing space is found after digits, break
        if (currentByte == (byte) ' ') {
            break;
        }

        // Check for invalid embedded spaces or NULs inside the number
        if (currentByte == 0 || currentByte == (byte) ' ') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        // Check that the byte is a valid octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
