public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        if (current == '\r') {
            int next = super.read();
            if (next != '\n' && next != -1) {
                ((ExtendedBufferedReader)this).unread(next);
            } else if (next == '\n') {
                // consume '\n' after '\r'
            }
        }
    }
    lastChar = current;
    return lastChar;
}
