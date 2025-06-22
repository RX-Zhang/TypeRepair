public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    // Delegate to parseOctalOrBinary to handle both octal and binary formats correctly
    return parseOctalOrBinary(buffer, offset, length);
}
