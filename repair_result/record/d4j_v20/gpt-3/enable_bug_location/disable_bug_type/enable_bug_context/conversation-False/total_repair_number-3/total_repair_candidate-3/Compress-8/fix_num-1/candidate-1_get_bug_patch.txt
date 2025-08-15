public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (buffer == null) {
        throw new IllegalArgumentException("buffer is null");
    }
    if (length < 2) {
        throw new IllegalArgumentException("length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;
    boolean foundDigit = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            // NUL byte indicates end of the octal number
            break;
        }

        // Skip leading spaces and zeros
        if (currentByte == (byte) ' ' || currentByte == '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                // space after digits means end of number
                break;
            }
        }

        stillPadding = false;
        // Check if current byte is a valid octal digit (0-7)
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }
        foundDigit = true;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    if (!foundDigit) {
        // No valid octal digit found
        throw new IllegalArgumentException("No valid octal digit found");
    }

    return result;
}
