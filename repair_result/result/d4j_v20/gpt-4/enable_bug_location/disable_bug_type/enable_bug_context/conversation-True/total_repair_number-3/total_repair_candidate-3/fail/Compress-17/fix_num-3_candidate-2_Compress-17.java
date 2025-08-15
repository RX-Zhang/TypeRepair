public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // Skip leading spaces and NULs
    int start = offset;
    int end = offset + length;

    while (start < end && (buffer[start] == ' ' || buffer[start] == 0)) {
        start++;
    }
    if (start == end) {
        return 0L;
    }

    // Must have trailing NUL or space
    // Skip trailing NULs and spaces
    int last = end - 1;
    while (last >= start && (buffer[last] == 0 || buffer[last] == ' ')) {
        last--;
    }
    if (last < start) {
        return 0L;
    }

    long result = 0;
    for (int i = start; i <= last; i++) {
        final byte currentByte = buffer[i];
        if (currentByte < '0' || currentByte > '7'){
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, currentByte));
        }
        result = (result << 3) + (currentByte - '0');
    }
    return result;
}
