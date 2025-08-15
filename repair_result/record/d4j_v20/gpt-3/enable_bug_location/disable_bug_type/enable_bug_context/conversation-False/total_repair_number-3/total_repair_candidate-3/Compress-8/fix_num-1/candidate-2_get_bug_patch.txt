public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    // first check that the last byte is either NUL or space
    byte lastByte = buffer[end - 1];
    if (lastByte != 0 && lastByte != (byte) ' ') {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end - 1, lastByte));
    }

    // also check that the buffer is not all NULs or spaces
    boolean allPadding = true;
    for (int i = start; i < end; i++) {
        final byte b = buffer[i];
        if (b != 0 && b != (byte) ' ') {
            allPadding = false;
            break;
        }
    }
    if (allPadding) {
        throw new IllegalArgumentException("Buffer is all zeros or spaces");
    }

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (stillPadding) {
            // Skip leading spaces and zeros
            if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
                continue;
            }
            stillPadding = false;
        }

        // Once padding skipped, if encounter space or NUL, treat as end of parsing
        if (currentByte == 0 || currentByte == (byte) ' ') {
            break;
        }

        if (currentByte < (byte) '0' || currentByte > (byte) '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }
        result = (result << 3) + (currentByte - (byte) '0'); // convert from ASCII octal digit
    }

    return result;
}
