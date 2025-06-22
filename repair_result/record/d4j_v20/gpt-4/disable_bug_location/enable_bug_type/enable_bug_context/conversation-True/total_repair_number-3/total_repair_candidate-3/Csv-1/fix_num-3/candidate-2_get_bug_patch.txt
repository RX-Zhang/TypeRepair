public int read() throws IOException {
    int current = super.read();
    if (current == '\r') {
        lineCounter++;
        int next = lookAhead();
        if (next == '\n') {
            super.read(); // consume '\n' after '\r'
            lastChar = '\n';
        } else {
            lastChar = '\r';
        }
    } else if (current == '\n') {
        lineCounter++;
        lastChar = '\n';
    } else {
        lastChar = current;
    }
    return lastChar;
}
