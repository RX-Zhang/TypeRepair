public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        // Handle \r\n sequence correctly: if current is \r, peek next char and skip incrementing lineCounter again for \n
        if (current == '\r') {
            int next = super.read();
            if (next != '\n') {
                // Not \r\n, so reset reading position by pushing back next char
                if (next != -1) {
                    if (super instanceof java.io.PushbackReader) {
                        ((java.io.PushbackReader) super).unread(next);
                    } else {
                        // fallback: not much we can do here without PushbackReader, ignore
                    }
                }
            } else {
                // next == '\n', consume it, no need to count lineCounter again
                current = next; // update current to \n to reflect lastChar read
            }
        }
    }
    lastChar = current;
    return lastChar;
}
