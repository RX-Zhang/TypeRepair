public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // Check for a zero byte at the start indicating a zero value
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
    byte trailer = buffer[end - 1];
    while (start < end && (trailer == 0 || trailer == ' ')) {
        end--;
        trailer = buffer[end - 1];
    }
    if (start == end) {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, trailer));
    }

    // Special case: if the first byte is a NUL or space and the rest are zeros or spaces
    // treat as zero value
    if (start == offset && (buffer[start] == 0 || buffer[start] == ' ')) {
        for (int i = start; i < end; i++) {
            byte b = buffer[i];
            if (b != 0 && b != ' ') {
                throw new IllegalArgumentException(
                        exceptionMessage(buffer, offset, length, i, b));
            }
        }
        return 0L;
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
