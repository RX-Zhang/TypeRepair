public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long result = 0;
    int end = offset + length;
    int start = offset;

    if (length < 2) {
        throw new IllegalArgumentException("Length " + length + " must be at least 2");
    }
    if (buffer == null) {
        throw new IllegalArgumentException("Buffer must not be null");
    }
    if (offset < 0 || length < 0 || offset + length > buffer.length) {
        throw new IllegalArgumentException("Invalid offset or length for buffer");
    }

    // If the first byte is zero, the value is zero
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

    // Must have trailing NUL or space - at least one trailing blank or NULL
    if (start == end) {
        throw new IllegalArgumentException(
            exceptionMessage(buffer, offset, length, start - 1, (byte) ' '));
    }
    byte trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' ') {
        end--;
    } else {
        throw new IllegalArgumentException(
            exceptionMessage(buffer, offset, length, end - 1, trailer));
    }
    // May have additional trailing NULs or spaces
    if (end > start) {
        trailer = buffer[end - 1];
        if (trailer == 0 || trailer == ' ') {
            end--;
        }
    }

    // Now parse the octal digits between start and end
    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        // Valid octal digits: '0' (48) to '7' (55)
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII octal representation
    }

    return result;
}
