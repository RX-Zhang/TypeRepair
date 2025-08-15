public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long result = 0;
    int end = offset + length;
    int start = offset;

    if (buffer == null) {
        throw new IllegalArgumentException("Buffer must not be null");
    }
    if (offset < 0 || length < 2 || offset + length > buffer.length) {
        throw new IllegalArgumentException("Invalid offset/length: offset=" + offset + ", length=" + length + ", buffer length=" + buffer.length);
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

    if (start >= end) {
        throw new IllegalArgumentException("No digits found in octal field");
    }

    // Must have trailing NUL or space
    byte trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' ') {
        end--;
    } else {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end - 1, trailer));
    }
    // May have additional NULs or spaces
    if (end - 1 >= start) {
        trailer = buffer[end - 1];
        if (trailer == 0 || trailer == ' ') {
            end--;
        }
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
