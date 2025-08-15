public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
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
