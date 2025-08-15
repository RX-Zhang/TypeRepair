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
        // For unrecognized escape sequences, return the escape char and then the character,
        // so that the escape is preserved in the output (e.g. \N or \a)
        // But since this method only returns one int, and the read char is already consumed,
        // we cannot pushback easily here. Instead, return the escape char itself to indicate
        // that the escape was not recognized. The caller needs to handle this by inserting escape char + c.
        // So here we return the char as-is, and the caller (Lexer) must prepend escape char to it.
        return c;
    }
}
