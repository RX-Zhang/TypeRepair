public static int formatLongOctalOrBinaryBytes(
    final long value, final byte[] buf, final int offset, final int length) {

    // Check for valid input parameters
    if (buf == null) {
        throw new IllegalArgumentException("Buffer cannot be null.");
    }
    if (offset < 0 || offset + length > buf.length) {
        throw new ArrayIndexOutOfBoundsException("Offset and length must be within the buffer's bounds.");
    }
    if (length <= 0) {
        throw new IllegalArgumentException("Length must be greater than zero.");
    }

    // Check whether we are dealing with UID/GID or SIZE field
    final long maxAsOctalChar = length == TarConstants.UIDLEN ? TarConstants.MAXID : TarConstants.MAXSIZE;

    final boolean negative = value < 0;
    if (!negative && value <= maxAsOctalChar) { // OK to store as octal chars
        return formatLongOctalBytes(value, buf, offset, length);
    }

    if (length < 9) {
        formatLongBinary(value, buf, offset, length, negative);
    } else {
        formatBigIntegerBinary(value, buf, offset, length, negative);
    }

    buf[offset] = (byte) (negative ? 0xff : 0x80);
    return offset + length;
}
