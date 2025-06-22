public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    boolean seenDigit = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            // NUL is treated as end of string and padding, stop parsing
            break;
        }

        if (currentByte == (byte) ' ') {
            if (stillPadding) {
                // Leading spaces, skip
                continue;
            } else {
                // Trailing spaces denote end of number
                break;
            }
        }

        if (currentByte == (byte) '0' && stillPadding) {
            // Leading zero padding can be skipped
            continue;
        }

        stillPadding = false;

        // Now currentByte must be octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        seenDigit = true;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    if (!seenDigit) {
        // Means all were either spaces or zeros only padding without digits, invalid
        throw new IllegalArgumentException(
            exceptionMessage(buffer, offset, length, offset, (byte) 0));
    }

    return result;
}
