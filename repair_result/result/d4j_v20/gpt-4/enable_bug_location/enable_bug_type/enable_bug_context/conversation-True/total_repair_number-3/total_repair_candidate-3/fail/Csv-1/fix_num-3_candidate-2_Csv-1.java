public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || (lastChar != '\r' && current == '\r')) {
        lineCounter++;
    }
    lastChar = current;
    return lastChar;
}
