public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Should be at least 2 bytes long");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        // End at NUL or space
        if (currentByte == 0 || currentByte == (byte) ' ') {
            break;
        }

        // Skip leading spaces and zeros
        if (stillPadding) {
            if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
                continue;
            }
            stillPadding = false;
        }

        // Check for invalid characters
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII octal digit
    }

    return result;
}
