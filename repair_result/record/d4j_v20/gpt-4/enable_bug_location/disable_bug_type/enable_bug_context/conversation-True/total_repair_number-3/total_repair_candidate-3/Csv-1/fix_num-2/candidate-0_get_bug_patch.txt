public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        if (current == '\r') {
            int nextChar = super.read();
            if (nextChar != '\n' && nextChar != -1) {
                ((PushbackReader) this).unread(nextChar);
            } else if (nextChar == '\n') {
                // if the line separator is \r\n, count only once
            }
        }
    }
    lastChar = current;
    return lastChar;
}
