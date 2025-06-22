int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash)
    final int c = in.read();
    if (c == END_OF_STREAM) {
        throw new IOException("EOF whilst processing escape sequence");
    }

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
    case FF: // These are valid escape sequences
    case TAB: // Tabs do not typically need escaping, but included for completeness
    case BACKSPACE: // Backspace is included as a valid escape
        return c;
    default:
        // Now check for meta-characters
        // Returning the character as-is is appropriate for non-escaped characters
        return c;
    }
}
