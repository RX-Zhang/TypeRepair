public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (buffer == null) {
        throw new IllegalArgumentException("Buffer cannot be null");
    }
    if (offset < 0 || length < 2 || offset + length > buffer.length) {
        throw new IllegalArgumentException("Invalid offset/length for buffer");
    }

    long result = 0;
    int end = offset + length;
    int start = offset;

    if (buffer[start] == 0) {
        return 0L;
    }

    // Skip leading spaces
    while (start < end){
        if (buffer[start] == ' '){
            start++;
        } else {
            break;
        }
    }

    // Trim all trailing NULs and spaces.
    // The ustar and POSIX tar specs require a trailing NUL or
    // space but some implementations use the extra digit for big
    // sizes/uids/gids ...
    while (start < end && (buffer[end - 1] == 0 || buffer[end - 1] == ' ')) {
        end--;
    }

    if (start == end) {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, buffer[start]));
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
