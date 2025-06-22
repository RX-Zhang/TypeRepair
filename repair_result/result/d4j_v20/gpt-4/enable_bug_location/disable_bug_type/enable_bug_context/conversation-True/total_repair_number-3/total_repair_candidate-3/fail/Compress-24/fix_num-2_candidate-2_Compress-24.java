public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    // Use parseOctalOrBinary which supports both octal and binary formats,
    // fixing the original bug which only supported strict octal.
    return parseOctalOrBinary(buffer, offset, length);
}
