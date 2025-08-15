int readEscape() throws IOException {
    // the escape char has just been read (normally a backslash or configured escape char)
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
        // Return the character as is including cases like 'N' (e.g. MySQL \N)
        // or any other escaped character which should remain unchanged.
        return c;
    }
}
