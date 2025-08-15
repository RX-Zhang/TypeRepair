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

        // NUL terminates parsing
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

        // Space after digits terminates parsing
        if (currentByte == (byte) ' ') {
            break;
        }

        // Validate byte is octal digit
        if (currentByte < (byte) '0' || currentByte > (byte) '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - (byte) '0'); // convert from ASCII
    }

    return result;
}
