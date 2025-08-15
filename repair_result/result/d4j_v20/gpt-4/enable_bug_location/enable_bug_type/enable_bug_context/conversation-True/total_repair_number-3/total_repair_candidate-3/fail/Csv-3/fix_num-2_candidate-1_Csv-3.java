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
    case 'N':
        // Return literal 'N' to preserve MySQL \N null representation
        return 'N';
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // Return the character as is to preserve escaping for unknown sequences
        return c;
    }
}
