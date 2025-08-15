public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    // Use parseOctalOrBinary instead to handle large values and binary format
    return parseOctalOrBinary(buffer, offset, length);
}
