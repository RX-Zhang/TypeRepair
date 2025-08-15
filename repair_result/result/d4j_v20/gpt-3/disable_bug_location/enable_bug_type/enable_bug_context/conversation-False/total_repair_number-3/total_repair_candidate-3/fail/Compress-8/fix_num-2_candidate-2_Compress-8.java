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
            // Trailing NUL indicates end of value
            break;
        }

        // Leading spaces or zeros are padding
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            // Space after digits indicates end of value
            if (currentByte == (byte) ' ') {
                break;
            }
        }

        stillPadding = false;

        // Validate digit is octal
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        foundDigit = true;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    if (!foundDigit) {
        throw new IllegalArgumentException("No octal digits found in buffer");
    }

    return result;
}
