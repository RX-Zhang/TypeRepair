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
    case '\\': // Proper handling of escape char itself
        return '\\';
    case escape: // Handling the configured escape char
        return escape;
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // return the character as is, do not omit the escape char effect
        return c;
    }
}
