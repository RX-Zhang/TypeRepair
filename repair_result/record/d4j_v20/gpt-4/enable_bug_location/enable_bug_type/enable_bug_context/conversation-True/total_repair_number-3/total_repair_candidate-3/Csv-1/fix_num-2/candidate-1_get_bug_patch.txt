public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        int nextChar = lookAhead();
        if (!(current == '\r' && nextChar == '\n')) {
            lineCounter++;
        }
    }
    lastChar = current;
    return lastChar;
}
