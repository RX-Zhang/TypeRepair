public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Input length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;
    boolean foundDigit = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) { // NUL byte, end of number
            // break only if we've found some digits
            break;
        }

        if (currentByte == (byte) ' ') {
            if (stillPadding) {
                continue; // skip leading spaces
            } else {
                // once digits started, space means end of number
                break;
            }
        }

        if (currentByte == (byte) '0') {
            if (stillPadding) {
                continue; // skip leading zeros
            }
            // if we are here, zero is valid digit after non-padding digits
        }

        stillPadding = false;

        // Now validate that currentByte is an octal digit 0-7
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        foundDigit = true;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    // If no digits found throw exception
    if (!foundDigit) {
        throw new IllegalArgumentException("No octal digits found in input");
    }

    return result;
}
