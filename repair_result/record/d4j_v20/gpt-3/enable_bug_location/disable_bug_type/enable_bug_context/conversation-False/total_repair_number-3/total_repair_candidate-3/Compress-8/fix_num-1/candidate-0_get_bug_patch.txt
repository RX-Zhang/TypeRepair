public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;
    boolean foundDigit = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            // Only allow trailing NULs (once digits have started, break)
            break;
        }

        // Skip leading spaces or zeros as padding
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            // Space after digits means end of number
            if (currentByte == (byte) ' ') {
                break;
            }
        }

        // Once here, padding is over
        stillPadding = false;

        // Check for valid octal digit
        if (currentByte < (byte) '0' || currentByte > (byte) '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        foundDigit = true;
        result = (result << 3) + (currentByte - (byte) '0');
    }

    if (!foundDigit) {
        throw new IllegalArgumentException(
                "No octal digits found in the input: " + new String(buffer, offset, length));
    }

    return result;
}
