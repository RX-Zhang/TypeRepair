public int read() throws IOException {
    int current = super.read();
    if (current == '\r') {
        lineCounter++;
        // Check if the next character is not '\n' to avoid double counting line breaks
        int next = lookAhead();
        if (next == '\n') {
            // Consume the '\n' after '\r'
            super.read();
        }
    } else if (current == '\n') {
        lineCounter++;
    }
    lastChar = current;
    return lastChar;
}
