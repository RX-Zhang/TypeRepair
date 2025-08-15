public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Octal length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;
    boolean seenDigit = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            // NUL terminator - end of number
            break;
        }

        // Skip leading spaces and zeros when still padding
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                break;
            }
        }

        stillPadding = false;

        // Check for valid octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        seenDigit = true;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    if (!seenDigit) {
        throw new IllegalArgumentException(
                "No octal digit found in buffer " + new String(buffer, offset, length));
    }

    return result;
}
