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
        } else {
            break;
        }
    }

    // Trim all trailing NULs and spaces.
    // Keep trimming as long as the last byte is space or NUL and start < end
    while (end > start && (buffer[end - 1] == 0 || buffer[end - 1] == ' ')) {
        end--;
    }

    // If after trimming trailing spaces/NULs the length is zero return 0
    if (start == end) {
        return 0L;
    }

    for ( ; start < end; start++) {
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
