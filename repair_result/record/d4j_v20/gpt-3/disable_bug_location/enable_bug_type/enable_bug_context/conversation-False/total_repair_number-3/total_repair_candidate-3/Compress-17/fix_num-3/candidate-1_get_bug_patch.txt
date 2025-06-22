public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // Skip leading NULs and spaces (leading NULs are common in tar headers)
    while (start < end){
        if (buffer[start] == 0 || buffer[start] == ' '){
            start++;
        } else {
            break;
        }
    }

    if (start == end) {
        // Only nulls and spaces, interpret as 0
        return 0L;
    }

    // Must have trailing NUL or space, trim trailing NULs/spaces
    while (end > start) {
        byte trailer = buffer[end - 1];
        if (trailer == 0 || trailer == ' '){
            end--;
        } else {
            break;
        }
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
