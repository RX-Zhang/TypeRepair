public Base64() {
    this(false);
}

public Base64(boolean urlSafe) {
    this(urlSafe, false);
}

public Base64(boolean urlSafe, boolean isChunked) {
    this(urlSafe, isChunked, DEFAULT_BUFFER_SIZE);
}

public Base64(boolean urlSafe, boolean isChunked, int bufferSize) {
    super();
    this.encodeTable = urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    this.lineLength = isChunked ? CHUNK_SIZE : 0;
    this.lineSeparator = isChunked ? CHUNK_SEPARATOR : null;
    this.decodeSize = bufferSize;
    this.encodeSize = 4 * (bufferSize / 3);
    this.buffer = new byte[encodeSize];
    this.pos = 0;
    this.readPos = 0;
    this.currentLinePos = 0;
    this.modulus = 0;
    this.eof = false;
    this.x = 0;
}

@Override
public Object encode(Object pObject) throws EncoderException {
    if (!(pObject instanceof byte[])) {
        throw new EncoderException("Parameter supplied to Base64 encode is not a byte[]");
    }
    return encode((byte[]) pObject);
}

@Override
public byte[] encode(byte[] pArray) {
    if (pArray == null || pArray.length == 0) {
        return pArray;
    }
    reset();
    encode(pArray, 0, pArray.length);
    encode(pArray, 0, -1);
    byte[] encoded = new byte[pos];
    System.arraycopy(buffer, 0, encoded, 0, pos);
    return encoded;
}

private void reset() {
    this.pos = 0;
    this.readPos = 0;
    this.currentLinePos = 0;
    this.modulus = 0;
    this.eof = false;
    this.x = 0;
}

void encode(byte[] in, int inPos, int inAvail) {
    if (eof) {
        return;
    }
    // inAvail < 0 is how we're informed of EOF in the underlying data we're encoding.
    if (inAvail < 0) {
        eof = true;
        if (buffer == null || buffer.length - pos < encodeSize) {
            resizeBuffer();
        }
        switch (modulus) {
            case 1:
                buffer[pos++] = encodeTable[(x >> 2) & MASK_6BITS];
                buffer[pos++] = encodeTable[(x << 4) & MASK_6BITS];
                // URL-SAFE skips the padding to further reduce size.
                if (encodeTable == STANDARD_ENCODE_TABLE) {
                    buffer[pos++] = PAD;
                    buffer[pos++] = PAD;
                }
                break;

            case 2:
                buffer[pos++] = encodeTable[(x >> 10) & MASK_6BITS];
                buffer[pos++] = encodeTable[(x >> 4) & MASK_6BITS];
                buffer[pos++] = encodeTable[(x << 2) & MASK_6BITS];
                // URL-SAFE skips the padding to further reduce size.
                if (encodeTable == STANDARD_ENCODE_TABLE) {
                    buffer[pos++] = PAD;
                }
                break;
        }
        if (lineLength > 0 && pos > 0) {
            System.arraycopy(lineSeparator, 0, buffer, pos, lineSeparator.length);
            pos += lineSeparator.length;
        }
    } else {
        for (int i = 0; i < inAvail; i++) {
            if (buffer == null || buffer.length - pos < encodeSize) {
                resizeBuffer();
            }
            modulus = (++modulus) % 3;
            int b = in[inPos++];
            if (b < 0) {
                b += 256;
            }
            x = (x << 8) + b;
            if (0 == modulus) {
                buffer[pos++] = encodeTable[(x >> 18) & MASK_6BITS];
                buffer[pos++] = encodeTable[(x >> 12) & MASK_6BITS];
                buffer[pos++] = encodeTable[(x >> 6) & MASK_6BITS];
                buffer[pos++] = encodeTable[x & MASK_6BITS];
                currentLinePos += 4;
                if (lineLength > 0 && lineLength <= currentLinePos) {
                    System.arraycopy(lineSeparator, 0, buffer, pos, lineSeparator.length);
                    pos += lineSeparator.length;
                    currentLinePos = 0;
                }
            }
        }
    }
}
