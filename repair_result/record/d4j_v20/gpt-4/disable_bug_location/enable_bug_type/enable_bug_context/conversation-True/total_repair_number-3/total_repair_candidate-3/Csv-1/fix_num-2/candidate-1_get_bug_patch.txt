public int read() throws IOException {
    int current = super.read();
    if (current == '\r') {
        lineCounter++;
        int next = super.read();
        if (next != '\n' && next != -1) {
            // push back the next character if it's not a newline or end of stream
            if (this instanceof PushbackReader) {
                ((PushbackReader)this).unread(next);
            } else {
                // handle other cases if needed
            }
        } else {
            current = next;
        }
    } else if (current == '\n') {
        lineCounter++;
    }
    lastChar = current;
    return lastChar;
}
