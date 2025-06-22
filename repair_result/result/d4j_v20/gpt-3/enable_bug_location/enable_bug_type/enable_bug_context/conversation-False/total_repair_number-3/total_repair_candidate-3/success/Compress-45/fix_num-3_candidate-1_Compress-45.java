public static int formatLongOctalOrBinaryBytes(
    final long value, final byte[] buf, final int offset, final int length) {

    // Check whether we are dealing with UID/GID or SIZE field
    final long maxAsOctalChar = length == TarConstants.UIDLEN ? TarConstants.MAXID : TarConstants.MAXSIZE;

    final boolean negative = value < 0;
    if (!negative && value <= maxAsOctalChar) { // OK to store as octal chars
        return formatLongOctalBytes(value, buf, offset, length);
    }

    if (length < 9) {
        formatLongBinary(value, buf, offset, length, negative);
        buf[offset] = (byte) (negative ? 0xff : 0x80);
        return offset + length;
    }

    // Check for maximum value before calling formatBigIntegerBinary
    if (value > Long.MAX_VALUE) {
        throw new IllegalArgumentException("Value " + value + " exceeds maximum allowed value for " + length + " byte field.");
    }
    
    formatBigIntegerBinary(value, buf, offset, length, negative);
    buf[offset] = (byte) (negative ? 0xff : 0x80);
    return offset + length;
}
