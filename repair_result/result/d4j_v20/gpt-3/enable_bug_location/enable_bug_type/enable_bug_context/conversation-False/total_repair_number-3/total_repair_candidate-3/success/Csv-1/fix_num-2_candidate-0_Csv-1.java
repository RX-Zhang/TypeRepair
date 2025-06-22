public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        if (lastChar != '\r' || current != '\n') {
            lineCounter++;
        }
    }
    lastChar = current;
    return lastChar;
}
