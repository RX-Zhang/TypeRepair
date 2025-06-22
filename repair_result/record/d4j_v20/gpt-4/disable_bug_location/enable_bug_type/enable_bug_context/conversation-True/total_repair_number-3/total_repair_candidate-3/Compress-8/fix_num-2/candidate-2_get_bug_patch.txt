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

        // If currentByte is NUL (0), this marks the end of the number
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces and leading zeros as padding
        if (stillPadding) {
            if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
                continue;
            }
            stillPadding = false;
        }

        // If trailing space encountered after numbers start, break
        if (currentByte == (byte) ' ') {
            break;
        }

        // Check that the byte is a valid octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII octal digit
    }

    return result;
}
