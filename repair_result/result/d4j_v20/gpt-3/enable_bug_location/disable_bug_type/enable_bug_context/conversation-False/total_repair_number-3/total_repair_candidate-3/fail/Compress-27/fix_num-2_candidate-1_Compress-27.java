public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // Find first non-null or non-space byte
    while (start < end) {
        if (buffer[start] == 0 || buffer[start] == ' ') {
            start++;
        } else {
            break;
        }
    }

    // If all bytes are NULs or spaces, return 0
    if (start == end) {
        return 0L;
    }

    // Trim all trailing NULs and spaces.
    // The ustar and POSIX tar specs require a trailing NUL or
    // space but some implementations use the extra digit for big
    // sizes/uids/gids ...
    while (end > start) {
        byte trailer = buffer[end - 1];
        if (trailer == 0 || trailer == ' ') {
            end--;
        } else {
            break;
        }
    }
    if (start == end) {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, buffer[start]));
    }

    for ( ;start < end; start++) {
        final byte currentByte = buffer[start];
        // CheckStyle:MagicNumber OFF
        if (currentByte < '0' || currentByte > '7'){
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
        // CheckStyle:MagicNumber ON
    }

    return result;
}
