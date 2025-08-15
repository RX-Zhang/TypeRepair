public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long result = 0;
    int end = offset + length;
    int start = offset;

    if (length < 2) {
        throw new IllegalArgumentException("Length " + length + " must be at least 2");
    }

    // Skip leading NULs
    while (start < end && buffer[start] == 0) {
        start++;
    }

    // Skip leading spaces
    while (start < end && buffer[start] == ' ') {
        start++;
    }

    // Must have trailing NUL or space
    if (end <= start) {
        return 0L;
    }

    byte trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' ') {
        end--;
        // May have additional NULs or spaces
        while (end > start) {
            trailer = buffer[end - 1];
            if (trailer == 0 || trailer == ' ') {
                end--;
            } else {
                break;
            }
        }
    } else {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end - 1, trailer));
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
