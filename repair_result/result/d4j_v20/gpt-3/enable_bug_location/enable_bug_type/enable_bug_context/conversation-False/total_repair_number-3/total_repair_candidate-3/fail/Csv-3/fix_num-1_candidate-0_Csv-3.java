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
    case TAB: // Tabs do not need to be escaped, but we handle them here
    case BACKSPACE: // Backspace is also valid
        return c;
    default:
        // Now check for meta-characters
        return c; // Return the character as is for unexpected chars
    }
}
