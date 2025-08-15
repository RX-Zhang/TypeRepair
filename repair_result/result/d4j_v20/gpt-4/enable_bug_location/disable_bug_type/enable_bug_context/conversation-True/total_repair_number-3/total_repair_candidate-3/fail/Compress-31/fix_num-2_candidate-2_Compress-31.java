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

    // Check for embedded NUL or spaces within the string (not just leading or trailing)
    for (int i = start; i < end; i++) {
        if (buffer[i] == 0 || buffer[i] == ' ') {
            // continue if trailing, break if embedded
            // trailing NULs or spaces are handled later
            if (i != end - 1 && buffer[i] == ' ') {
                throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, buffer[i]));
            }
            if (i != end - 1 && buffer[i] == 0) {
                throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, buffer[i]));
            }
        }
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
    byte trailer = buffer[end - 1];
    while (start < end && (trailer == 0 || trailer == ' ')) {
        end--;
        trailer = buffer[end - 1];
    }

    for ( ;start < end; start++) {
        final byte currentByte = buffer[start];
        if (currentByte == 0) {
            break;
        }
        if (currentByte < '0' || currentByte > '7'){
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
