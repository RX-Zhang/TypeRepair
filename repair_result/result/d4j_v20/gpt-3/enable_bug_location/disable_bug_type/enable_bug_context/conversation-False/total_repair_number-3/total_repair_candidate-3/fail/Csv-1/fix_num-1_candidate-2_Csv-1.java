public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        if (current == '\r') {
            int next = super.read();
            if (next != '\n' && next != -1) {
                super.reset();
            } else {
                current = next;
            }
        }
    }
    lastChar = current;
    return lastChar;
}
