public int read() throws IOException {
    int current = super.read();
    if (current == '\r') {
        lineCounter++;
        // Look ahead to check for '\n' following '\r'
        int next = super.read();
        if (next != '\n' && next != -1) {
            // If not '\n', push back the character for next read
            if (next != -1) {
                // Use an internal buffer or a mechanism to unread the character
                // but since not available, store in lastChar to be returned next time
                lastChar = next;
            }
        } else if (next == '\n') {
            // If '\r\n', count as one line separator, increment handled already
        }
        return current;
    } else if (current == '\n') {
        lineCounter++;
    }
    lastChar = current;
    return lastChar;
}
