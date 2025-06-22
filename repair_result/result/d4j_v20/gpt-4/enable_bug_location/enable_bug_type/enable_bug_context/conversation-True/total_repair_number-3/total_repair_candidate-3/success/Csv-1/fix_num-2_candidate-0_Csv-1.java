public int read() throws IOException {
    int current = super.read();
    if (current == '\r') {
        lineCounter++;
    } else if (current == '\n') {
        if (lastChar != '\r') {
            lineCounter++;
        }
    }
    lastChar = current;
    return lastChar;
}
