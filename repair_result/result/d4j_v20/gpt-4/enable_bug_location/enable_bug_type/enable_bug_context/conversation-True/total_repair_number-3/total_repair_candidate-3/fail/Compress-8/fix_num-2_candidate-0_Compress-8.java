public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }
    
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    // Check if all bytes are zero (NULs)
    boolean allNuls = true;
    for (int i = start; i < end; i++) {
        if (buffer[i] != 0) {
            allNuls = false;
            break;
        }
    }
    if (allNuls) {
        throw new IllegalArgumentException("All bytes are NUL - invalid input");
    }

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) { // Trailing NUL means end
            break;
        }

        // Skip leading spaces and NULs
        if (stillPadding && (currentByte == (byte) ' ' || currentByte == (byte) '0')) {
            continue;
        }

        // Trailing space or NUL marks end of octal number
        if (currentByte == (byte) ' ' ) {
            break;
        }

        stillPadding = false;

        // If outside of allowed range (octal digits)
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }
    
    return result;
}
