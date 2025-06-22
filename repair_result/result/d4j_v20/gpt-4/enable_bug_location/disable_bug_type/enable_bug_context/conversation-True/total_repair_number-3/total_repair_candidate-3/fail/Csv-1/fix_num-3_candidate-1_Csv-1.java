public int read() throws IOException {
    int current = super.read();
    if (current == '\n' || current == '\r') {
        lineCounter++;
        if (current == '\r') {
            int next = super.read();
            if (next != '\n' && next != -1) {
                // unread if next char is not '\n' or EOF
                if (this instanceof java.io.PushbackReader) {
                    ((java.io.PushbackReader)this).unread(next);
                } else {
                    // fallback if not a PushbackReader
                    // not necessary for the current context, but we acknowledge the possibility
                }
            } else {
                current = next; // consume '\n' after '\r'
            }
        }
    }
    lastChar = current;
    return lastChar;
}
