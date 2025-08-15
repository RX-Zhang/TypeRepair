public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long result = 0;
    int end = offset + length;
    int start = offset;

    if (length < 2) {
        throw new IllegalArgumentException("Length " + length + " must be at least 2");
    }

    // If the first byte is NUL, the value is zero
    if (buffer[start] == 0) {
        return 0L;
    }

    // Skip leading spaces
    while (start < end) {
        if (buffer[start] == ' ') {
            start++;
        } else {
            break;
        }
    }

    // Must have trailing NUL or space: skip trailing NUL or spaces (may be multiple)
    int trailerIndex = end - 1;
    while (trailerIndex >= start) {
        byte trailer = buffer[trailerIndex];
        if (trailer == 0 || trailer == ' ') {
            trailerIndex--;
        } else {
            break;
        }
    }

    if (trailerIndex < start) {
        // No digits found, treat as zero
        return 0L;
    }

    for (int i = start; i <= trailerIndex; i++) {
        final byte currentByte = buffer[i];
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII octal digit
    }

    return result;
}
