public int read() throws IOException {
    int current = super.read();
    if (current == '\r' || current == '\n') {
        lineCounter++;
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
