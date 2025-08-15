void decode(byte[] in, int inPos, int inAvail) {
    if (eof) {
        return;
    }
    if (inAvail < 0) {
        eof = true;
        return;
    }
    try {
        for (int i = 0; i < inAvail; i++) {
            if (buffer == null || buffer.length - pos < decodeSize) {
                resizeBuffer();
            }
            if (inPos >= in.length) { // boundary check to prevent ArrayIndexOutOfBoundsException
                eof = true;
                break;
            }
            byte b = in[inPos++];
            if (b == PAD) {
                // We're done.
                eof = true;
                break;
            } else {
                if (b >= 0 && b < DECODE_TABLE.length) {
                    int result = DECODE_TABLE[b];
                    if (result >= 0) {
                        modulus = (++modulus) % 4;
                        x = (x << 6) + result;
                        if (modulus == 0) {
                            if (buffer == null || buffer.length - pos < 3) {
                                resizeBuffer();
                            }
                            buffer[pos++] = (byte) ((x >> 16) & MASK_8BITS);
                            buffer[pos++] = (byte) ((x >> 8) & MASK_8BITS);
                            buffer[pos++] = (byte) (x & MASK_8BITS);
                        }
                    }
                }
            }
        }

        // Two forms of EOF as far as base64 decoder is concerned: actual
        // EOF (-1) and first time '=' character is encountered in stream.
        // This approach makes the '=' padding characters completely optional.
        if (eof && modulus != 0) {
            x = x << 6;
            switch (modulus) {
                case 2:
                    if (buffer == null || buffer.length - pos < 1) {
                        resizeBuffer();
                    }
                    x = x << 6;
                    buffer[pos++] = (byte) ((x >> 16) & MASK_8BITS);
                    break;
                case 3:
                    if (buffer == null || buffer.length - pos < 2) {
                        resizeBuffer();
                    }
                    buffer[pos++] = (byte) ((x >> 16) & MASK_8BITS);
                    buffer[pos++] = (byte) ((x >> 8) & MASK_8BITS);
                    break;
            }
        }
    } catch (Exception e) {
        // Reset internal state on exception to prevent partial corrupt states
        reset();
        throw e;
    }
}
