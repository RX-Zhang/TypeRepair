public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (buffer == null) {
        throw new IllegalArgumentException("Buffer is null");
    }
    if (offset < 0 || length < 2 || offset + length > buffer.length) {
        throw new IllegalArgumentException("Invalid offset and/or length");
    }

    long result = 0;
    int end = offset + length;
    int start = offset;

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

    // Trim all trailing NULs and spaces.
    // The ustar and POSIX tar specs require a trailing NUL or
    // space but some implementations use the extra digit for big
    // sizes/uids/gids ...
    if (start == end) {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, (byte) ' '));
    }
    byte trailer = buffer[end - 1];
    while (start < end && (trailer == 0 || trailer == ' ')) {
        end--;
        if (end > 0) {
            trailer = buffer[end - 1];
        } else {
            break;
        }
    }
    if (start == end) {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, trailer));
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        // CheckStyle:MagicNumber OFF
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
        // CheckStyle:MagicNumber ON
    }

    return result;
}
