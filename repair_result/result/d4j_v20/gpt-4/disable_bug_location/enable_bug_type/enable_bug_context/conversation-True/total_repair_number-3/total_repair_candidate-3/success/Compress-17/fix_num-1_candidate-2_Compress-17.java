public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long result = 0;
    int end = offset + length;
    int start = offset;

    if (length < 2) {
        throw new IllegalArgumentException("Length " + length + " must be at least 2");
    }

    // Buffer length check to avoid IndexOutOfBoundsException
    if (buffer == null || buffer.length < offset + length) {
        throw new IllegalArgumentException("Buffer is null or too small for the given offset and length");
    }

    // If first byte is NUL, the value is zero
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

    if (start == end) {
        // No digits found
        throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, start, (byte) 0));
    }

    // Must have trailing NUL or space
    byte trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' ') {
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

    if (start == end) {
        // No digits found after trimming
        throw new IllegalArgumentException(exceptionMessage(buffer, offset, length, start, (byte) 0));
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        // Check if current byte is a valid octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII octal digit
    }

    return result;
}
