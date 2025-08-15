public Base64() {
    this(false);
}

public Base64(boolean urlSafe) {
    this(0, CHUNK_SEPARATOR, urlSafe);
}

public Base64(int lineLength, byte[] lineSeparator, boolean urlSafe) {
    super();
    if (lineLength > 0) {
        if (lineSeparator == null) {
            throw new IllegalArgumentException("lineSeparator cannot be null if lineLength > 0");
        }
        this.lineLength = (lineLength / 4) * 4;
        this.lineSeparator = new byte[lineSeparator.length];
        System.arraycopy(lineSeparator, 0, this.lineSeparator, 0, lineSeparator.length);
    } else {
        this.lineLength = 0;
        this.lineSeparator = null;
    }
    this.encodeTable = urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    this.decodeSize = 3;
    this.encodeSize = 4;
}
