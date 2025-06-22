public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("len="+length+" < 2");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;

    // Check that all bytes are either NUL or space in the trailing bytes
    // starting from the end, to validate proper termination
    int lastNonNull = -1;
    for (int i = end - 1; i >= offset; i--) {
        final byte currentByte = buffer[i];
        if (currentByte == 0 || currentByte == (byte) ' ') {
            continue;
        } else {
            lastNonNull = i;
            break;
        }
    }

    if (lastNonNull == -1) {
        throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, offset, (byte) 0));
    }

    for (int i = offset; i <= lastNonNull; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
        }

        // Skip leading spaces and zeros
        if (stillPadding) {
            if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
                continue;
            } else {
                stillPadding = false;
            }
        }

        // Check digits from '0' to '7'
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
        }
        result = (result << 3) + (currentByte - '0');
    }

    return result;
}
