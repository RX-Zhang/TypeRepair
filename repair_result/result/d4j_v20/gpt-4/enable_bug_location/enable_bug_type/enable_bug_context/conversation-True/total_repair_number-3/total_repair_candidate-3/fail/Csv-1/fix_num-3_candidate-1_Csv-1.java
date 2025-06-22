public int read() throws IOException {
    int current = super.read();
    if (current == '\r' || current == '\n') {
        lineCounter++;
        if (current == '\r') {
            int nextChar = lookAhead();
            if (nextChar == '\n') {
                current = super.read();
            }
        }
    }
    lastChar = current;
    return lastChar;
}
