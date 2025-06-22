int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash)
    final int c = in.read();
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
    case '\'':
        return '\'';
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // For unrecognized escape sequences, return the escaped character as-is,
        // including the escape char if needed (for example \N in MySQL).
        // So in order to preserve the escape char in token content,
        // the lexer should be designed to add it explicitly.
        // Here we return the char, and lexer logic should handle this.
        return c;
    }
}
