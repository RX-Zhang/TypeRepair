public int read(byte b[], int offset, int len) throws IOException {
    if (b == null) {
        throw new NullPointerException();
    } else if (offset < 0 || len < 0) {
        throw new IndexOutOfBoundsException();
    } else if (offset > b.length || offset + len > b.length) {
        throw new IndexOutOfBoundsException();
    } else if (len == 0) {
        return 0;
    } else {
        while (base64.hasData() || fillBuffer()) {
            int readLen = base64.readResults(b, offset, len);
            if (readLen != 0) {
                return readLen;
            }
        }
        return -1;
    }
}

private boolean fillBuffer() throws IOException {
    byte[] buf = new byte[doEncode ? 4096 : 8192];
    int c = in.read(buf);
    if (c == -1) {
        base64.setEOF();
        return false;
    }
    if (doEncode) {
        base64.encode(buf, 0, c);
    } else {
        base64.decode(buf, 0, c);
    }
    return true;
}
