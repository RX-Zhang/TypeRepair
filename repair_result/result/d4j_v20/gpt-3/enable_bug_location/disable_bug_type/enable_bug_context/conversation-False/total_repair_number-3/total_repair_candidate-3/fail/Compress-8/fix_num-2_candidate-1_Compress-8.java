public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) { // NUL terminator
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

        // Only octal digits allowed
        if (currentByte < (byte) '0' || currentByte > (byte) '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - (byte) '0'); // convert from ASCII
    }

    // After processing, at least one digit must have been found (not all padding)
    if (stillPadding) {
        throw new IllegalArgumentException("Value is all padding");
    }

    return result;
}
