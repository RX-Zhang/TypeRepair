public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        // Handle possible \r\n sequence
        if (current == '\r') {
            int next = super.read();
            if (next != '\n' && next != -1) {
                lastChar = next;
                return lastChar;
            }
            if (next == '\n') {
                lineCounter++;
            }
        }
    }
    lastChar = current;
    return lastChar;
}
