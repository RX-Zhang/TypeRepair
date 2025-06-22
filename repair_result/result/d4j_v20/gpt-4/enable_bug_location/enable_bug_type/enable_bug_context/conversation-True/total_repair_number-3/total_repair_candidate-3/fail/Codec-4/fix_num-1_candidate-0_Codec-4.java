public Base64() {
    this(false);
}

public Base64(boolean urlSafe) {
    this(urlSafe, DEFAULT_BUFFER_SIZE);
}

public Base64(boolean urlSafe, int size) {
    this.encodeTable = urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    this.lineLength = CHUNK_SIZE;
    this.lineSeparator = CHUNK_SEPARATOR;
    this.decodeSize = size;
    this.encodeSize = size;
    this.buffer = null;
    this.pos = 0;
    this.readPos = 0;
    this.currentLinePos = 0;
    this.modulus = 0;
    this.eof = false;
    this.x = 0;
}
