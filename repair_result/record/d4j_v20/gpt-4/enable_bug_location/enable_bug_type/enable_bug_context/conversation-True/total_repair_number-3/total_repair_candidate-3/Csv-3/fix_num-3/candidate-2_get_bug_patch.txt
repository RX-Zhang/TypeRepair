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
    case CR:
    case LF:
    case FF:
    case TAB:
    case BACKSPACE:
        return c;
    case 'N':
        // preserve \N sequence (used for MySQL null)
        return 'N';
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // For all other characters, preserve the escape character by returning the escape char followed by the character
        // Since this method returns int, we must handle escape insertion in calling method.
        // Here, just return the character to be consistent.
        return c;
    }
}
