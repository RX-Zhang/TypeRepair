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

    // Skip leading spaces and leading NULs
    while (start < end){
        if (buffer[start] == ' ' || buffer[start] == 0){
            start++;
        } else {
            break;
        }
    }

    // Must have trailing NUL or space or NULs and spaces at the end
    while (end > start) {
        byte trailer = buffer[end - 1];
        if (trailer == 0 || trailer == ' '){
            end--;
        } else {
            break;
        }
    }

    if (start == end) {
        // If no digits found
        return 0L;
    }

    for (; start < end; start++) {
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
