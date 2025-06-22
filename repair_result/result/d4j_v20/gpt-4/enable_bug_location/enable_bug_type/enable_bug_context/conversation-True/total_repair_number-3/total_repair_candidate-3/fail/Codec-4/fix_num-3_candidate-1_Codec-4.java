public Base64() {
    this(false);
}

public Base64(boolean urlSafe) {
    this.encodeTable = urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    this.encodeSize = 4;
    this.decodeSize = 3;
    this.lineLength = 0;
    this.lineSeparator = null;
    this.buffer = null;
    this.pos = 0;
    this.readPos = 0;
    this.modulus = 0;
    this.eof = false;
    this.x = 0;
}

public Object encode(Object pObject) {
    if (pObject == null) {
        return null;
    } else if (pObject instanceof byte[]) {
        return encode((byte[]) pObject);
    } else {
        throw new EncoderException("Parameter supplied to Base64 encode is not a byte[]");
    }
}

public byte[] encode(byte[] pArray) {
    reset();

    if (pArray == null || pArray.length == 0) {
        return pArray;
    }

    encode(pArray, 0, pArray.length);
    encode(null, 0, -1);

    byte[] result = new byte[pos];
    readResults(result, 0, result.length);
    return result;
}
