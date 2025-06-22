public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long result = 0;
    int end = offset + length;
    int start = offset;

    // Input validation for length
    if (length < 2) {
        throw new IllegalArgumentException("Length " + length + " must be at least 2");
    }

    // Check if the buffer is empty or starts with a NUL byte
    if (buffer.length == 0 || buffer[start] == 0) {
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
        // Ensure we do not go out of bounds
        if (end <= start) {
            break;
        }
        trailer = buffer[end - 1];
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        // Check for embedded NULs
        if (currentByte == 0) {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, currentByte));
        }
        // Check valid octal character
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}
