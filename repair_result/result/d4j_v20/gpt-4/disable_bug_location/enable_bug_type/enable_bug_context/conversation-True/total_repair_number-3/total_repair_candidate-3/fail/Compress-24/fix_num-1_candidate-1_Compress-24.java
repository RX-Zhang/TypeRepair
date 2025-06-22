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
    // The ustar and POSIX tar specs require a trailing NUL or
    // space but some implementations use the extra digit for big
    // sizes/uids/gids ...
    byte trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' ') {
        end--;
    } else {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end - 1, trailer));
    }
    trailer = buffer[end - 1];
    while (start < end - 1 && (trailer == 0 || trailer == ' ')) {
        end--;
        trailer = buffer[end - 1];
    }

    for (; start < end; start++) {
        final byte currentByte = buffer[start];
        // Restrict to valid octal digits only: '0' to '7'
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
    }

    return result;
}

public static long parseOctalOrBinary(final byte[] buffer, final int offset, final int length) {
    if (length <= 0) {
        throw new IllegalArgumentException("Length must be positive, was: " + length);
    }
    if (buffer == null) {
        throw new IllegalArgumentException("Buffer must not be null");
    }
    if (offset < 0 || length < 0 || offset + length > buffer.length) {
        throw new IllegalArgumentException("Invalid offset/length parameters");
    }

    // If the first bit of the first byte is zero, parse as octal
    if ((buffer[offset] & 0x80) == 0) {
        return parseOctal(buffer, offset, length);
    }

    // Otherwise parse as binary (signed or unsigned)
    final boolean negative = buffer[offset] == (byte) 0xff;
    if (length < 9) {
        return parseBinaryLong(buffer, offset, length, negative);
    }
    return parseBinaryBigInteger(buffer, offset, length, negative);
}
