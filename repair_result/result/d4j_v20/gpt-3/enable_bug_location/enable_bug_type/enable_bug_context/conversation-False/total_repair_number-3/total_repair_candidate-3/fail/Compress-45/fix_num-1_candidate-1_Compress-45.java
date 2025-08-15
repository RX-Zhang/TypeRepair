public static int formatLongOctalOrBinaryBytes(
    final long value, final byte[] buf, final int offset, final int length) {

    // Check whether we are dealing with UID/GID or SIZE field
    final long maxAsOctalChar = length == TarConstants.UIDLEN ? TarConstants.MAXID : TarConstants.MAXSIZE;

    final boolean negative = value < 0;
    if (!negative && value <= maxAsOctalChar) { // OK to store as octal chars
        return formatLongOctalBytes(value, buf, offset, length);
    }

    // Add boundary check for length
    if (length < 1 || length > buf.length - offset) {
        throw new IllegalArgumentException("Invalid length: " + length);
    }

    if (length < 9) {
        formatLongBinary(value, buf, offset, length, negative);
    } else {
        formatBigIntegerBinary(value, buf, offset, length, negative);
    }

    buf[offset] = (byte) (negative ? 0xff : 0x80);
    return offset + length;
}
