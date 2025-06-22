public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    // Input validation
    if (buffer == null || offset < 0 || length < 2 || offset + length > buffer.length) {
        throw new IllegalArgumentException("Invalid input: buffer cannot be null, offset must be non-negative, and length must be at least 2.");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces
        if (currentByte == (byte) ' ' || currentByte == '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                break;
            }
        }

        // Must have trailing NUL or space
        // May have additional NUL or space

        stillPadding = false;
        // CheckStyle:MagicNumber OFF
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
        // CheckStyle:MagicNumber ON
    }

    // Ensure the result ends with a NUL or space
    if (stillPadding || (end > start && (buffer[end - 1] != 0 && buffer[end - 1] != ' '))) {
        throw new IllegalArgumentException("Invalid input: must end with NUL or space.");
    }

    return result;
}
