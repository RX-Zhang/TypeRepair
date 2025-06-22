public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    // Use parseOctalOrBinary for robust parsing, which handles both octal and binary formats.
    return parseOctalOrBinary(buffer, offset, length);
}
