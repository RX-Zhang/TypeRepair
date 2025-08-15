public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    int end = offset + length;
    int start = offset;

    // Skip leading spaces
    while (start < end){
        if (buffer[start] == ' '){
            start++;
        } else {
            break;
        }
    }

    // If the entire buffer is zeros or spaces, return 0
    boolean allZeroOrSpace = true;
    for (int i = start; i < end; i++) {
        if (buffer[i] != 0 && buffer[i] != ' ') {
            allZeroOrSpace = false;
            break;
        }
    }
    if (allZeroOrSpace) {
        return 0L;
    }

    // Trim all trailing NULs and spaces.
    // The ustar and POSIX tar specs require a trailing NUL or
    // space but some implementations use the extra digit for big
    // sizes/uids/gids ...
    byte trailer = buffer[end - 1];
    while (start < end && (trailer == 0 || trailer == ' ')) {
        end--;
        if (start < end) {
            trailer = buffer[end - 1];
        }
    }

    long result = 0;
    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        if (currentByte == 0) {
            break;
        }
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
