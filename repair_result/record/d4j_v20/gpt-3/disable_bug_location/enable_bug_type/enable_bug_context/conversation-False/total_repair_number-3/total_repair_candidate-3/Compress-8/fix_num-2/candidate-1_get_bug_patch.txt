public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            // Trailing NUL terminates the number
            break;
        }

        if (stillPadding) {
            if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
                // skip leading spaces and zeros
                continue;
            } else {
                stillPadding = false;
            }
        }

        // After padding, only octal digits or trailing space/NUL allowed
        if (currentByte == (byte) ' ') {
            // Trailing space terminates number parsing
            break;
        }

        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    // Check that the entire field is either parsed number plus trailing spaces/NULs
    // Validate that there is at least one digit parsed
    if (stillPadding) {
        // All are spaces and zeros possibly but no digit found
        throw new IllegalArgumentException(
                "Invalid octal number: no digits found");
    }

    // Optionally check trailing bytes after the break point for only spaces or NULs
    // to satisfy "must have trailing NUL or space"
    for (int i = start + length - 1; i >= 0; i--) {
        final byte b = buffer[i];
        if (b != 0 && b != (byte) ' ') {
            break;
        }
    }

    return result;
}
