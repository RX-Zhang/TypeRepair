public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    if (buffer[start] == 0) {
        return 0L;
    }

    // Skip leading spaces
    while (start < end){
        if (buffer[start] == ' '){
            start++;
        } else if (buffer[start] == 0) {  // embedded NUL in leading spaces - invalid
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, buffer[start]));
        } else {
            break;
        }
    }

    // Trim all trailing NULs and spaces.
    // The ustar and POSIX tar specs require a trailing NUL or
    // space but some implementations use the extra digit for big
    // sizes/uids/gids ...
    byte trailer = buffer[end - 1];
    while (start < end && (trailer == 0 || trailer == ' ')) {
        end--;
        if (end > start) {
            trailer = buffer[end - 1];
        }
    }

    for ( ;start < end; start++) {
        final byte currentByte = buffer[start];
        if (currentByte == 0) {
            // embedded NUL inside octal digits - invalid
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, currentByte));
        }
        if (currentByte == ' ') {
            // embedded space inside octal digits is invalid
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, currentByte));
        }
        // CheckStyle:MagicNumber OFF
        if (currentByte < '0' || currentByte > '7'){
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        // CheckStyle:MagicNumber ON
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
