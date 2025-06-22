public int read() throws IOException {
    int current = super.read();
    if (current == '\r') {
        lineCounter++;
        if (lookAhead() == '\n') {
            super.read(); // consume '\n' after '\r'
        }
    } else if (current == '\n') {
        lineCounter++;
    }
    lastChar = current;
    return lastChar;
}
