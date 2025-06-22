public int read() throws IOException {
    int current = super.read();
    if (lastChar == '\r' && current != '\n') {
        lineCounter++;
    }
    if (current == '\n') {
        lineCounter++;
    }
    lastChar = current;
    return lastChar;
}
