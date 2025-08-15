public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2 bytes long");
    }

    long result = 0L;
    boolean stillPadding = true;
    int end = offset + length;
    int start = offset;

    boolean seenNUL = true;

    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];

        if (currentByte == 0) {
            // We allow trailing NUL bytes only after the number ends
            seenNUL = true;
            break;
        }

        // Skip leading spaces and zeros
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            if (currentByte == (byte) ' ') {
                seenNUL = false; // space padding after number is allowed
                break;
            }
        }

        stillPadding = false;

        // Validate octal digits
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII
        seenNUL = false;
    }

    // After finishing parse loop, check that any trailing bytes are NUL or space
    for (int i = offset; i < offset + length; i++) {
        byte b = buffer[i];
        if (b != 0 && b != (byte) ' ' && b != (byte) '\0') {
            // The original loop should have caught invalid bytes,
            // but we ensure trailing bytes are NUL or space
            if (i >= offset && i < offset + length) {
                // If this byte is after the digit sequence, it must be space or NUL
                if (!((b == 0) || (b == ' '))) {
                    throw new IllegalArgumentException(
                            exceptionMessage(buffer, offset, length, i, b));
                }
            }
        }
    }

    // If entire buffer is NULs (seenNUL true as no digits found), throw exception
    if (stillPadding && result == 0L) {
        throw new IllegalArgumentException("Invalid octal value: buffer contains only padding or NULs");
    }

    return result;
}
