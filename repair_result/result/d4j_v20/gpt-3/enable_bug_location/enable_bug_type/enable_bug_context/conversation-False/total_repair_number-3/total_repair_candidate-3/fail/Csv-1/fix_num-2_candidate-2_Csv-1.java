public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        // Handle possible \r\n as a single newline
        if (current == '\r') {
            super.mark(1);
            int next = super.read();
            if (next != '\n') {
                super.reset();
            }
        }
    }
    lastChar = current;
    return lastChar;
}
