int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash)
    final int c = in.read();
    if (c == END_OF_STREAM) {
        throw new IOException("EOF whilst processing escape sequence");
    }
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
    case '\r':
        return CR;
    case '\n':
        return LF;
    case '\f':
        return FF;
    case '\t':
        return TAB;
    case '\b':
        return BACKSPACE;
    default:
        // Return the char as-is, including cases like 'N' (for \N) etc.
        return c;
    }
}
