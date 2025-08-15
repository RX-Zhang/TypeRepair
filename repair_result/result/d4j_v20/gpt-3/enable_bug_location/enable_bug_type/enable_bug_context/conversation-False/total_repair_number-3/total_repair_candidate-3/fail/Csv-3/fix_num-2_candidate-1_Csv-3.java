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
        return c; // Return the character as is for these cases
    case END_OF_STREAM:
        throw new IOException("EOF whilst processing escape sequence");
    default:
        // Now check for meta-characters
        return c; // Return the character as is for meta-characters
    }
}
