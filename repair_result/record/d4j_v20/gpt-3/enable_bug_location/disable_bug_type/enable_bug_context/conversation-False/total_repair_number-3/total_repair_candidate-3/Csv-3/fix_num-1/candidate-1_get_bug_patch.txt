int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash or custom escape char)
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
        // For all other characters we return the escaped character literally including meta like N for MySQL \N (null),
        // and any other characters that should be escaped literally.
        // This fixes test cases where escape sequences like \N or \a are expected to keep the escape char in output.
        // To do that we must return the escape character followed by c.
        // However, the method returns int, so we cannot return two characters at once.
        // The caller must handle assembling those characters into the token.
        // Therefore, here we return the character itself, but the caller,
        // if it detects unrecognized escape, will include the escape char and this char.
        return c;
    }
}
