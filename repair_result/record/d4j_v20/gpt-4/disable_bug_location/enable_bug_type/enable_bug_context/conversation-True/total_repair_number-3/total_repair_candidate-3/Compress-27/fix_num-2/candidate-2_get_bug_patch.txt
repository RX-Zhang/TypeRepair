public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    // Delegate to parseOctalOrBinary which handles both octal and binary encodings safely
    return parseOctalOrBinary(buffer, offset, length);
}
