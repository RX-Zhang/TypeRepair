public int read(byte b[], int offset, int len) throws IOException {
    if (b == null) {
        throw new NullPointerException();
    } else if (offset < 0 || len < 0) {
        throw new IndexOutOfBoundsException();
    } else if (offset > b.length || offset + len > b.length) {
        throw new IndexOutOfBoundsException();
    } else if (len == 0) {
        return 0;
    }

    int readLen = 0;

    while (readLen == 0) {
        if (!base64.hasData()) {
            byte[] buf = new byte[doEncode ? 4096 : 8192];
            int c = in.read(buf);
            if (c == -1) {
                // No more data to read from the underlying stream
                // flush any remaining data in base64
                base64.encode(new byte[0], 0, 0);
                // or decode with zero-length input
                // whichever is appropriate
            } else if (c > 0) {
                // A little optimization to avoid System.arraycopy()
                // when possible.
                if (doEncode) {
                    base64.encode(buf, 0, c);
                } else {
                    base64.decode(buf, 0, c);
                }
            }
        }
        readLen = base64.readResults(b, offset, len);
        if (readLen == 0 && base64.hasData() == false) {
            // no more data to read and no more buffered data
            return -1;
        }
    }
    return readLen;
}
