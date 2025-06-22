public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    // Input validation
    if (buffer == null || length < 2) {
        throw new IllegalArgumentException("Buffer must not be null and length must be at least 2 bytes long.");
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

    // Final validation for trailing NUL or space
    if (stillPadding) {
        throw new IllegalArgumentException("Input must have trailing NUL or space.");
    }

    return result;
}
