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
        int readLen = 0;
        while (readLen == 0) {
            if (!base64.hasData()) {
                byte[] buf = new byte[doEncode ? 4096 : 8192];
                int c = in.read(buf);
                if (c == -1) {
                    // End of stream
                    if (!doEncode) {
                        base64.decode(buf, 0, 0);
                    } else {
                        base64.encode(buf, 0, 0);
                    }
                    // readResults() should now return -1 if no data left
                } else if (c > 0) {
                    if (doEncode) {
                        base64.encode(buf, 0, c);
                    } else {
                        base64.decode(buf, 0, c);
                    }
                }
            }
            readLen = base64.readResults(b, offset, len);
            if (readLen == 0 && in.available() == 0) {
                // no more input available and no data in buffer, so end
                return -1;
            }
        }
        return readLen;
    }
}
