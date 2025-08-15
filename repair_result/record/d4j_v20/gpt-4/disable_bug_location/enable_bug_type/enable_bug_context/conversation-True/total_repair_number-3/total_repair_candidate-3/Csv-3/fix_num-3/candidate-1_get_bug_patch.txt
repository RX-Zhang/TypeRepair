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
    case '\\': // Allow escape of escape char itself
    case '\'':
    case '\"':
    case delimiter:
        return c;
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // Return the char as is
        return c;
    }
}
