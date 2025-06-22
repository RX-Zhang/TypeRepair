int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash)
    final int c = in.read();
    if (c == END_OF_STREAM) {
        throw new IOException("EOF whilst processing escape sequence");
    }
    // Preserve escaped chars as literals (including the escape char itself)
    switch (c) {
        case 'r':
            return CR;
        case 'n':
            return LF;
        case 't':
            return TAB;
        case 'b':
            return BACKSPACE;
        case 'f':
            return FF;
        case '\\':
            return '\\';
        default:
            // Return the char literally (including 'N' or 'a') rather than swallowed
            return c;
    }
}
