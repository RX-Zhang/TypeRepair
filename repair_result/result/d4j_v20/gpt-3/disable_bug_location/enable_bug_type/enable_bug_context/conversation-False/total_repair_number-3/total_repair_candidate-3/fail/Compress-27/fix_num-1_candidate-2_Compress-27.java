public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // Skip leading NULs and spaces
    while (start < end && (buffer[start] == 0 || buffer[start] == ' ')) {
        start++;
    }

    if (start == end) {
        return 0L;
    }

    // Trim all trailing NULs and spaces.
    // The ustar and POSIX tar specs require a trailing NUL or
    // space but some implementations use the extra digit for big
    // sizes/uids/gids ...
    byte trailer = buffer[end - 1];
    while (start < end && (trailer == 0 || trailer == ' ')) {
        end--;
        if (end == 0) {
            break;
        }
        trailer = buffer[end - 1];
    }
    if (start == end) {
        return 0L;
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        // Accept NUL or space ONLY if at the end (trailer).
        if (currentByte == 0 || currentByte == ' ') {
            // ignore trailing spaces or NULs within loop
            continue;
        }
        if (currentByte < '0' || currentByte > '7'){
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
