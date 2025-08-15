public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Octal length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        // If we hit a NUL (0) or space after digits, stop parsing
        if (currentByte == 0 || currentByte == (byte) ' ') {
            if (stillPadding) {
                // If only padding so far, continue skipping
                continue;
            } else {
                // Trailing NUL or space is allowed, stop parsing here
                break;
            }
        }

        // If still skipping leading padding, skip zeros and spaces
        if (stillPadding) {
            if (currentByte == (byte) '0') {
                continue;
            } else {
                stillPadding = false; // first non-padding byte
            }
        }

        // Check for valid octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        stillPadding = false;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII octal digit
    }

    // If no digits were found, or only padding, throw exception
    if (stillPadding) {
        throw new IllegalArgumentException("No octal digits found");
    }

    return result;
}
