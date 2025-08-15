public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Octal length must be at least 2");
    }

    long result = 0;
    boolean seenDigit = false;
    int end = offset + length;
    int paddingStart = -1;

    for (int i = offset; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte == 0) {
            // Trailing NUL found, after any digits and padding
            break;
        }
        if (currentByte == (byte) ' ') {
            // Once we see a space after digits started, rest should be spaces or NULs
            if (!seenDigit) {
                // Leading spaces, skip
                continue;
            }
            // Mark start of padding if not already done
            if (paddingStart == -1) {
                paddingStart = i;
            }
            continue;
        }
        if (currentByte >= '0' && currentByte <= '7') {
            if (paddingStart != -1) {
                // Embedded digit after padding space detected -> invalid
                throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
            }
            seenDigit = true;
            result = (result << 3) + (currentByte - '0');
            continue;
        }

        // Invalid character (not space, NUL, or octal digit)
        throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, i, currentByte));
    }

    // Reject if no digits found
    if (!seenDigit) {
        throw new IllegalArgumentException("No octal digits found");
    }

    return result;
}
