public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long result = 0;
    int end = offset + length;
    int start = offset;

    if (length < 2) {
        throw new IllegalArgumentException("Length " + length + " must be at least 2");
    }

    boolean allNUL = true;
    for (int i = start; i < end; i++) {
        if (buffer[i] != 0) {
            allNUL = false;
            break;
        }
    }
    if (allNUL) {
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
        // All spaces or no digits
        return 0L;
    }

    // Must have trailing NUL or space
    byte trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' ') {
        end--;
    } else {
        // If last byte not NUL or space, try if one before last is NUL or space (some tar headers may be malformed)
        if (length > 1 && (buffer[end - 2] == 0 || buffer[end - 2] == ' ')) {
            end -= 2;
        } else {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, end - 1, trailer));
        }
    }

    // May have additional NUL or space
    while (end > start) {
        trailer = buffer[end - 1];
        if (trailer == 0 || trailer == ' ') {
            end--;
        } else {
            break;
        }
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
