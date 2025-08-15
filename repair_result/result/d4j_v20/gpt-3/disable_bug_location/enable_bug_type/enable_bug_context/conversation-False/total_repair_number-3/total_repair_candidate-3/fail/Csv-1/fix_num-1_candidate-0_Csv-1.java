public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        // Handle CRLF: if \r followed by \n, consume \n without incrementing line
        if (current == '\r') {
            super.mark(1);
            int nextChar = super.read();
            if (nextChar != '\n') {
                super.reset();
            }
        }
    }
    lastChar = current;
    return lastChar;
}
