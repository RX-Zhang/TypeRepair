public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2");
    }
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;
    int index = offset;

    while (index < end) {
        final byte currentByte = buffer[index];

        // If we hit a NUL byte then the number ends here
        if (currentByte == 0) {
            break;
        }

        // Skip leading spaces and zeros as padding
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                index++;
                continue;
            }
            // If padding ended, a space means number ends here
            if (currentByte == (byte) ' ') {
                break;
            }
            // Else trailing zeros after digits are invalid here as number continues
        }

        stillPadding = false;

        // Valid octal digits are from '0' to '7' inclusive
        if (currentByte < (byte) '0' || currentByte > (byte) '7') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, index, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert from ASCII
        index++;
    }

    // Check that the rest (after index) until offset+length is zeros or spaces or NULs
    for (int i = index; i < end; i++) {
        byte b = buffer[i];
        if (b != 0 && b != (byte) ' ' && b != (byte) '\0') {
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, i, b));
        }
    }

    return result;
}
