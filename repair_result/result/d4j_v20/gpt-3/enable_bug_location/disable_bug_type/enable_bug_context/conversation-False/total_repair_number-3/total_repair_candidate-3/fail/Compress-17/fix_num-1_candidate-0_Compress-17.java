public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // If the buffer starts with a NUL byte, treat as zero
    if (buffer[start] == 0) {
        return 0L;
    }

    // Skip leading spaces or NULs
    while (start < end){
        if (buffer[start] == ' ' || buffer[start] == 0){
            start++;
        } else {
            break;
        }
    }

    // Must have trailing NUL or space, one or more
    int last = end - 1;
    while (last >= start && (buffer[last] == 0 || buffer[last] == ' ')) {
        last--;
    }

    // Now parse octal digits between start and last (inclusive)
    for (int i = start; i <= last; i++) {
        final byte currentByte = buffer[i];
        if (currentByte < '0' || currentByte > '7'){
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
