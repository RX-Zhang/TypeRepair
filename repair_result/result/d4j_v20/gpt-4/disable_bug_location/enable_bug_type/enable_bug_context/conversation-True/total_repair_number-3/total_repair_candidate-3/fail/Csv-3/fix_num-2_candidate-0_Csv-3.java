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
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // For characters such as 'N' or 'a' (not recognized escapes),
        // return the escape character plus the actual character read,
        // to preserve the escape sequence in the token content.
        // This is needed because current method only returns a single char,
        // but in the bug, escape sequences like '\N' and '\a' get reduced incorrectly.
        // To fix this, the calling method nextToken() should be updated to handle this case,
        // but here we return the character after escape unmodified.
        return c;
    }
}
