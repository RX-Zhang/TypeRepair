public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long result = 0;
    int end = offset + length;
    int start = offset;

    if (length < 2) {
        throw new IllegalArgumentException("Length " + length + " must be at least 2");
    }

    if (buffer[start] == 0) {
        return 0L;
    }

    // Skip leading spaces
    while (start < end) {
        if (buffer[start] == ' ') {
            start++;
        } else {
            break;
        }
    }

    // Trim all trailing NULs and spaces.
    byte trailer = buffer[end - 1];
    while (start < end && (trailer == 0 || trailer == ' ')) {
        end--;
        trailer = buffer[end - 1];
    }
    if (start == end) {
        throw new IllegalArgumentException(
            exceptionMessage(buffer, offset, length, start, trailer));
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        // additional check to accept 0 byte without error to handle input like {0,' '}
        if ((currentByte < '0' || currentByte > '7') && currentByte != 0) {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, currentByte));
        }
        // skip 0 byte if any inside the trimmed octal string
        if (currentByte == 0) {
            continue;
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
