public int read() throws IOException {
    int current = super.read();

    if (current == '\n' || current == '\r') {
        lineCounter++;
        // Handle \r\n as a single line ending by checking the next char if current is \r
        if (current == '\r') {
            super.mark(1);
            int next = super.read();
            if (next != '\n') {
                super.reset();
            } else {
                // consume \n after \r, so no extra line increment occurs for \n
                current = next;
            }
        }
    }    
    lastChar = current;
    return lastChar;
}
