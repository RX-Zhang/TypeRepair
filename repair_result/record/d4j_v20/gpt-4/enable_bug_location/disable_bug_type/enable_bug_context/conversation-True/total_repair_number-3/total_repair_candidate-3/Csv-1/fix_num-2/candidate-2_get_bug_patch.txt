public int read() throws IOException {
    int current = super.read();
    if (current == '\r') {
        lineCounter++;
        int next = super.read();
        if (next != '\n' && next != -1) {
            lastChar = next;
            return lastChar;
        }
        current = next;
    } else if (current == '\n') {
        lineCounter++;
    }
    lastChar = current;
    return lastChar;
}
