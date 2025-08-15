public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // Skip leading NULs and spaces
    while (start < end && (buffer[start] == 0 || buffer[start] == ' ')) {
        start++;
    }

    // Must have trailing NUL or space
    if (end == offset) {
        throw new IllegalArgumentException(
                "Empty field at offset " + offset);
    }

    byte trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' ') {
        end--;
    } else {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end - 1, trailer));
    }

    // May have additional trailing NULs or spaces
    while (end > start && (buffer[end - 1] == 0 || buffer[end - 1] == ' ')) {
        end--;
    }

    if (start == end) {
        return 0L;
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        // CheckStyle:MagicNumber OFF
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
        // CheckStyle:MagicNumber ON
    }

    return result;
}
