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
    case '\\': // properly handle escaped escape char
        return '\\';
    case escape: // handle escape of the escape char properly
        return escape;
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // Return the character itself to preserve escaped sequences such as \N or \a
        return c;
    }
}
