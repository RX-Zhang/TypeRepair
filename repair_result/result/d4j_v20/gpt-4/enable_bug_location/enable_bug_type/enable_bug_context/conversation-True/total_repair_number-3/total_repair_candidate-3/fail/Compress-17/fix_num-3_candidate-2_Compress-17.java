public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long result = 0;
    int end = offset + length;
    int start = offset;

    if (length < 2) {
        throw new IllegalArgumentException("Length " + length + " must be at least 2");
    }

    // If first byte is zero, the value is zero
    if (buffer[start] == 0) {
        return 0L;
    }

    // Skip leading spaces and NULs
    while (start < end && (buffer[start] == ' ' || buffer[start] == 0)) {
        start++;
    }

    if (start == end) {
        return 0L;
    }

    // Must have trailing NUL or space
    byte trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' ') {
        end--;
    } else {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end - 1, trailer));
    }

    // May have additional trailing NULs or spaces
    while (end > start && (buffer[end - 1] == 0 || buffer[end - 1] == ' ')) {
        end--;
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0');
    }

    return result;
}
