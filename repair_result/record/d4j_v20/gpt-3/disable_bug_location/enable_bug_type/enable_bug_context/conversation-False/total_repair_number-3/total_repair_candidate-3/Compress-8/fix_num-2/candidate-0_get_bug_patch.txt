public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2 bytes");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;
    boolean seenNull = false;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            seenNull = true;
            continue;
        }
        if (seenNull) {
            // Once a NUL character is seen, the rest must also be NUL or space
            if (currentByte != (byte) ' ' && currentByte != 0) {
                throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
            }
            continue;
        }

        // Skip leading spaces
        if (currentByte == (byte) ' ') {
            if (stillPadding) {
                continue;
            }
            // Space after value means end of value
            break;
        } else if (currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
        } else if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        stillPadding = false;
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    if (stillPadding) {
        // if only padding bytes (spaces or zeros), no valid digits found
        throw new IllegalArgumentException("No valid octal digits found");
    }

    return result;
}
