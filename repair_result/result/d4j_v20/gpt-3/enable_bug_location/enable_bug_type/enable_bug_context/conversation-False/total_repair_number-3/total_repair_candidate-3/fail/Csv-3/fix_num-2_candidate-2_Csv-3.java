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
    case FF: // These are control characters and can be treated as escaped
    case TAB: // Tabs do not need to be escaped, but we might handle them here for consistency
    case BACKSPACE: // Backspace can also be treated as escaped
        return c;
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // return the character as is for unrecognized escape sequences
        return c; // No need to indicate unexpected char, handle later if necessary
    }
}
