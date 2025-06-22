public Base64() {
    super();
    this.encodeTable = STANDARD_ENCODE_TABLE;
    this.lineLength = 0;
    this.lineSeparator = null;
    this.decodeSize = 3;
    this.encodeSize = 4;
}
