public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        if (current == '\r') {
            // peek ahead one char to skip \n in case of \r\n
            int next = super.read();
            if (next != '\n' && next != -1) {
                lastChar = next;
                return lastChar;
            }
            current = next;
        }
    }
    lastChar = current;
    return lastChar;
}
