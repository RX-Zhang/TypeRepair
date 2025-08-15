public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length should be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        // If we encounter a NUL byte, it should be only as trailing padding, stop parsing
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces and zeros as padding
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                break;
            }
        }

        stillPadding = false;

        // Validate that currentByte is an octal digit
        if (currentByte < (byte) '0' || currentByte > (byte) '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - (byte) '0'); // convert from ASCII octal digit
    }

    return result;
}
