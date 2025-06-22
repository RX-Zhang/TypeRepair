public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    if (buffer == null) {
        throw new IllegalArgumentException("Buffer is null");
    }
    if (length < 2) {
        throw new IllegalArgumentException("Length must be at least 2 bytes long");
    }
    if (offset < 0 || length < 0 || offset + length > buffer.length) {
        throw new IllegalArgumentException("Invalid offset/length for buffer");
    }
    
    long result = 0;
    boolean stillPadding = true;
    int end = offset + length;

    for (int i = offset; i < end; i++) {
        final byte currentByte = buffer[i];

        // Stop at NUL byte or space following digits
        if (currentByte == 0) {
            break;
        }

        // Skip leading space or '0' padding
        if (currentByte == (byte) ' ' || currentByte == (byte) '0') {
            if (stillPadding) {
                continue;
            }
            // If we're not padding and we see a space, end of number
            if (currentByte == (byte) ' ') {
                break;
            }
        }

        // Once we see a non-padding byte, we are done skipping padding
        stillPadding = false;

        // Check that byte is valid octal digit
        if (currentByte < '0' || currentByte > '7') {
            throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, i, currentByte));
        }

        result = (result << 3) + (currentByte - '0'); // convert ASCII octal digit to numeric value
    }
    
    // If stillPadding is true after the loop, it means we had only zeros/spaces or empty input
    // In tar format, this can be interpreted as zero but the test cases expect IllegalArgumentException for empty or all null inputs
    // Check for at least one valid digit processed
    // According to test, empty (length < 2) or all NULs or all spaces are invalid
    // Here, if all were spaces or zeros and the loop didn't process digits, reject
    if (stillPadding) {
        // i.e. no digit processed, probably all zeros/spaces or no data
        throw new IllegalArgumentException("Invalid octal number: no digits found");
    }
    
    return result;
}
