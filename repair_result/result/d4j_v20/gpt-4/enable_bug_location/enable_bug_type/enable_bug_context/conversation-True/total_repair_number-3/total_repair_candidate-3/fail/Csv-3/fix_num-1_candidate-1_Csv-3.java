int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash)
    final int c = in.read();
    switch (c) {
    case 'r':
        return CR;
    case 'n':
        // return the escape char + 'n' as literal, to preserve sequence like \N
        return escape;
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
        // return the escape char and the char read to preserve escaped chars like \N, \a etc
        // so the lexer does not swallow them
        in.unread(c);
        return escape;
    }
}
