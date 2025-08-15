public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // Empty or all zeros means zero
    if (buffer[offset] == 0) {
        return 0L;
    }

    int start = offset;
    int end = offset + length;

    // Skip leading spaces
    while (start < end && buffer[start] == ' ') {
        start++;
    }

    // Trim all trailing NULs and spaces.
    // The ustar and POSIX tar specs require a trailing NUL or
    // space but some implementations use the extra digit for big
    // sizes/uids/gids ...
    while (end > start && (buffer[end - 1] == 0 || buffer[end - 1] == ' ')) {
        end--;
    }

    if (start >= end) {
        throw new IllegalArgumentException("No octal digits found");
    }

    long result = 0;
    for (int i = start; i < end; i++) {
        final byte currentByte = buffer[i];
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i - offset, currentByte));
        }
        result = (result << 3) + (currentByte - '0');
    }

    return result;
}
