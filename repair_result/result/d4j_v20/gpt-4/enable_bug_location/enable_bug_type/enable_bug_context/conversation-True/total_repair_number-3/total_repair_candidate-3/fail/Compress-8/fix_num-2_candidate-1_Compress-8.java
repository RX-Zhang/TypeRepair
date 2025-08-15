public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length should be at least 2 bytes long");
    }

    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    // Verify trailer: last byte must be NUL (0) or space (32)
    byte lastByte = buffer[end - 1];
    if (lastByte != 0 && lastByte != (byte) ' ') {
        throw new IllegalArgumentException("Invalid trailer byte: " + lastByte);
    }

    // Check that all bytes after first non-NUL/space are valid octal digits or trailing space/NUL
    boolean foundNonPadding = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            // If any NUL is found inside the number (not trailer), it's invalid
            // But trailing NULs are allowed only at the end - so break here
            break;
        }

        // Skip leading spaces and zeros
        if (currentByte == (byte) ' ' || currentByte == '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                // space after number marks end of number
                break;
            }
        }

        // Once non-padding byte found
        stillPadding = false;

        // Validate octal character ('0' to '7')
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
