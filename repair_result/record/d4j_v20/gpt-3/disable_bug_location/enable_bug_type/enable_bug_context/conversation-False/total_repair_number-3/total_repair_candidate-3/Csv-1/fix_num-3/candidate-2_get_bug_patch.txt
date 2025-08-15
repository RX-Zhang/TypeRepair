public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        // Handle the case of \r\n to avoid double counting
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
