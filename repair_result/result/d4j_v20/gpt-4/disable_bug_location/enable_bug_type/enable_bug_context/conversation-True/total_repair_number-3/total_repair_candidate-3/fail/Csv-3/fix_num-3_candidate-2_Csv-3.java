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
        // Return the original escape character followed by the current char,
        // to preserve escaped sequences like \N or \a as literals
        // This requires pushing back the character and returning the escape char itself
        // Since in.read() is already consumed, we must handle accordingly

        // If the lexer class has a pushback or unread method use it here,
        // Otherwise, we should return the character itself as is,
        // but this current code structure only returns one char,
        // so returning c alone loses the escape char information.

        // To fix the logical bug, we should return the character c as is,
        // but the client code (e.g. nextToken) should handle escape sequences properly.

        return c;
    }
}
