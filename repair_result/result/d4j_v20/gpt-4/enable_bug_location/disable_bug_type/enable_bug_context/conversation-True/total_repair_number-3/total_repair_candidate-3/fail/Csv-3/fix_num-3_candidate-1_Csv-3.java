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
    case escape:
        // Escape character escaped by itself, return escape char
        return escape;
    default:
        // For MySQL \N (null) do not remove escape, leave as-is
        if (c == 'N') {
            return 'N';
        }
        // Return the character as-is (escaping something else)
        return c;
    }
}
