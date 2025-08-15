int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash)
    final int c = in.read();

    // Preserve the escape character if this is not a recognized escape sequence
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
        // For unrecognized escapes, preserve the escape character itself
        in.unread(c);
        return escape;
    }
}
