public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // Skip leading nulls
    while (start < end && buffer[start] == 0) {
        start++;
    }

    if (start == end) {
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

    if (start == end) {
        return 0L;
    }

    // Must have trailing NUL or space
    byte trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' '){
        end--;
    } else {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end - 1, trailer));
    }

    // May have additional trailing NULs or spaces
    while (end > start) {
        trailer = buffer[end - 1];
        if (trailer == 0 || trailer == ' ') {
            end--;
        } else {
            break;
        }
    }

    for ( ;start < end; start++) {
        final byte currentByte = buffer[start];
        if (currentByte < '0' || currentByte > '7'){
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
